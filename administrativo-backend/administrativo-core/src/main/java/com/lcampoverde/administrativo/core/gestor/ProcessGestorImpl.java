package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.dao.StatusUserDao;
import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.constant.error.ProcessError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ExecutionGestor;
import com.lcampoverde.administrativo.cliente.gestor.ProcessGestor;
import com.lcampoverde.administrativo.cliente.model.CatalogValue;
import com.lcampoverde.administrativo.cliente.model.Process;
import com.lcampoverde.administrativo.cliente.model.StatusUser;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ExecutionVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ProcessVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;
import com.lcampoverde.administrativo.cliente.repository.ProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Lazy
public class ProcessGestorImpl implements ProcessGestor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessGestorImpl.class);

    @Autowired
    @Lazy
    private ProcessRepository processRepository;

    @Autowired
    @Lazy
    private ExecutionGestor executionGestor;

    @Autowired
    @Lazy
    private StatusUserDao statusUserDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessRepository getProcessRepository() {
        return processRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessVO save(ProcessVO process) {
        try {
            validateExistsByName(process);
            Process pr = Process.builder()
                    .typeProcess(process.getTypeProcess())
                    .enabled(Boolean.TRUE)
                    .description(process.getDescription())
                    .name(process.getName())
                    .build();
            return process.toBuilder().id(processRepository.save(pr).getId()).build();
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ProcessVO> findAllProcess() {
        return processRepository.findByEnabled(Boolean.TRUE)
        .stream().map( p ->
            ProcessVO.builder()
                .id(p.getId())
                .description(p.getDescription())
                .name(p.getName())
                .catalogValueVO(
                    CatalogValueVO.builder()
                        .id(p.getCatalogValue().getId())
                        .keyWord(p.getCatalogValue().getKeyWord())
                        .name(p.getCatalogValue().getName())
                        .description(p.getCatalogValue().getDescription())
                        .catalogId(p.getCatalogValue().getCatalogId())
                        .build()
                )
                .build()
        ).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessVO findProcessById(Long id) {
        Optional<Process> process= processRepository.findById(id);
        return returnProcessVO(process);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessVO findProcessByName(String name) {
        Process process = processRepository.findByName(name);
        return returnProcessVO(Optional.of(process));
    }

    private ProcessVO returnProcessVO(Optional<Process> process) {
        return process.isPresent() ?
                ProcessVO.builder()
                        .id(process.get().getId())
                        .name(process.get().getName())
                        .description(process.get().getDescription())
                        .build()
                : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProcessById(Long id) {
        delProcess(processRepository.findById(id));
    }

    private void delProcess(Optional<Process> process) {
        try {
            if (process.isPresent()) {
                processRepository.save(process.get().toBuilder().enabled(Boolean.FALSE).build());
                executionGestor.deleteExecutionByProcessId(process.get().getId());
            } else {
                throw new AppException(ProcessError.NOT_EXIST.getConstant());
            }
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.DELETE.getConstant(), ex);
            throw new ErrorException(CrudError.DELETE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProcessByName(String name) {
        delProcess(Optional.of(processRepository.findByName(name)));
    }

    /**
     * {@inheritDoc}
     */
    public ProcessVO update(ProcessVO processVO) {
        try {
            validateExistsByName(processVO);
            Optional<Process> process = processRepository.findById(processVO.getId());
            if (process.isPresent()) {
                Process pr = process.get().toBuilder()
                        .description(processVO.getDescription())
                        .name(processVO.getName())
                        .typeProcess(processVO.getTypeProcess())
                        .build();
                return processVO.toBuilder().id(processRepository.save(pr).getId()).build();
            } else {
                throw new AppException(ProcessError.NOT_EXIST.getConstant());
            }
        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            logger.error(LoggerError.UPDATE.getConstant(), e);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }

    private void validateExistsByName(ProcessVO processVO) {
        if (existsByName(processVO.getName().trim())) {
            throw new AppException(ProcessError.EXIST.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProcessVO> findByUserId(Long userId) {
        return toProcessVO(statusUserDao.findByUserId(userId));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProcessVO> findByProcessId(Long processId) {
        return toProcessVO(statusUserDao.findByProcessId(processId));
    }

    private List<ProcessVO> toProcessVO(List<StatusUser> statusUser) {
        Map<Long, ProcessVO> processMap = new HashMap<>();
        statusUser.forEach(su -> {
            StatusVO status = StatusVO.builder()
                    .catalogValueVO(toCatalogValueVO(su.getStatus().getCatalogValue())
                    )
                    .id(su.getStatus().getId())
                    .observations(
                            su.getObservations().stream().map( o ->
                                    ObservationsVO.builder()
                                            .id(o.getId())
                                            .description(o.getDescription())
                                            .catalogValueVO(toCatalogValueVO(o.getCatalogValue()))
                                            .userId(su.getUser().getId())
                                            .build()
                            ).collect(Collectors.toSet())
                    )
                    .users(new HashSet<>(
                            Arrays.asList(
                                    SignUpRequest.builder()
                                            .userName(su.getUser().getUserName())
                                            .firstName(su.getUser().getFirstName())
                                            .lastName(su.getUser().getLastName())
                                            .fullName(su.getUser().getFullName())
                                            .id(su.getUser().getId())
                                            .photo(su.getUser().getPhoto())
                                            .build()
                            )
                    ))
                    .build();

            ExecutionVO execution = ExecutionVO.builder()
                    .statuses(new HashSet<>(Arrays.asList(status)))
                    .alias(su.getStatus().getExecution().getAlias())
                    .finishDate(su.getStatus().getExecution().getFinishDate())
                    .id(su.getStatus().getExecution().getId())
                    .build();

            if (processMap.containsKey(su.getStatus().getExecution().getProcessId())) {
                Optional<ExecutionVO> executionVO = processMap.get(su.getStatus().getExecution().getProcessId())
                        .getExecutions().stream()
                        .filter(e->e.getId().equals(su.getStatus().getExecution().getId()))
                        .findFirst();

                if (executionVO.isPresent()) {
                    Optional<StatusVO> statusVO =
                            executionVO.get().getStatuses().stream()
                                    .filter(st -> st.getId().equals(status.getId()))
                                    .findFirst();
                    if (statusVO.isPresent()) {
                        status.getUsers().addAll(statusVO.get().getUsers());
                        status.getObservations().addAll(statusVO.get().getObservations());
                    } else {
                        executionVO.get().getStatuses().add(status);
                        execution = executionVO.get();
                    }
                }
            }
            ProcessVO process = ProcessVO.builder()
                    .id(su.getStatus().getExecution().getProcessId())
                    .catalogValueVO(toCatalogValueVO(su.getStatus().getExecution().getProcess().getCatalogValue()))
                    .name(su.getStatus().getExecution().getProcess().getName())
                    .description(su.getStatus().getExecution().getProcess().getDescription())
                    .enabled(su.getStatus().getExecution().getProcess().getEnabled())
                    .executions(new HashSet<>(Arrays.asList(execution)))
                    .build();
            processMap.put(process.getId(), process);
        });
        return new ArrayList<>(processMap.values());
    }

    private CatalogValueVO toCatalogValueVO(CatalogValue catalogValue) {
        return CatalogValueVO.builder()
                .id(catalogValue.getId())
                .catalogId(catalogValue.getCatalogId())
                .description(catalogValue.getDescription())
                .keyWord(catalogValue.getKeyWord())
                .name(catalogValue.getName())
                .enabled(catalogValue.getEnabled())
                .build();
    }
}
