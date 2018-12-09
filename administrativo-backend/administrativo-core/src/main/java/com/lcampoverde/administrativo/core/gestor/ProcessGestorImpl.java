package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.constant.error.ProcessError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ExecutionGestor;
import com.lcampoverde.administrativo.cliente.gestor.ProcessGestor;
import com.lcampoverde.administrativo.cliente.model.Process;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ProcessVO;
import com.lcampoverde.administrativo.cliente.repository.ProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
}
