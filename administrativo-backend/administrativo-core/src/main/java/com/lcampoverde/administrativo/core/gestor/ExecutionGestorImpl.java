package com.lcampoverde.administrativo.core.gestor;

import com.lcampoverde.administrativo.cliente.constant.error.CrudError;
import com.lcampoverde.administrativo.cliente.constant.error.ExecutionError;
import com.lcampoverde.administrativo.cliente.constant.error.LoggerError;
import com.lcampoverde.administrativo.cliente.exception.AppException;
import com.lcampoverde.administrativo.cliente.exception.ErrorException;
import com.lcampoverde.administrativo.cliente.gestor.ExecutionGestor;
import com.lcampoverde.administrativo.cliente.model.Execution;
import com.lcampoverde.administrativo.cliente.model.StatusUser;
import com.lcampoverde.administrativo.cliente.model.nopersist.CatalogValueVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ConclusionsVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ExecutionVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObjectivesVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.ObservationsVO;
import com.lcampoverde.administrativo.cliente.model.nopersist.SignUpRequest;
import com.lcampoverde.administrativo.cliente.model.nopersist.StatusVO;
import com.lcampoverde.administrativo.cliente.repository.ConclusionsRepository;
import com.lcampoverde.administrativo.cliente.repository.ExecutionRepository;
import com.lcampoverde.administrativo.cliente.repository.ObjectivesRepository;
import com.lcampoverde.administrativo.cliente.repository.ObservationsRepository;
import com.lcampoverde.administrativo.cliente.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Lazy
public class ExecutionGestorImpl implements ExecutionGestor {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionGestorImpl.class);

    @Autowired
    @Lazy
    private ExecutionRepository executionRepository;

    @Autowired
    @Lazy
    private StatusRepository statusRepository;

    @Autowired
    @Lazy
    private ConclusionsRepository conclusionsRepository;

    @Autowired
    @Lazy
    private ObservationsRepository observationsRepository;

    @Autowired
    @Lazy
    private ObjectivesRepository objectivesRepository;

    @Override
    public ExecutionRepository getRepository() {
        return executionRepository;
    }

    @Override
    public Boolean existsByAlias(String alias) {
        return executionRepository.existsByAliasIgnoreCase(alias);
    }

    @Override
    public ExecutionVO save(ExecutionVO execution) {
        try {
            Execution ex = executionRepository.save(
                Execution.builder()
                    .alias(execution.getAlias())
                    .processId(execution.getProcessId())
                    .finishDate(execution.getFinishDate())
                    .build()
            );
            return execution.toBuilder().id(ex.getId()).build();
        } catch (Exception ex) {
            logger.error(LoggerError.SAVE.getConstant(), ex);
            throw new ErrorException(CrudError.SAVE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ExecutionVO> findExecutionByProcessIdAndDate(Long processId, Date dateIni, Date dateFin) {
        if (null == dateIni) {
            return getSetExecutionsByProcess(executionRepository.findExecutionByProcessId(processId));
        } else {
            return getSetExecutionsByProcess(executionRepository.findExecutionsByProcessIdAndCreatedAtBetween(processId, dateIni, null == dateFin ? dateIni : dateFin));
        }
    }

    private Set<ExecutionVO> getSetExecutionsByProcess(Set<Execution> ex) {
        return ex
            .stream()
            .map(x ->
                ExecutionVO
                    .builder()
                    .alias(x.getAlias())
                    .id(x.getId())
                    .processId(x.getProcessId())
                    .statuses(
                        new HashSet<>(
                            Arrays.asList(
                                StatusVO.builder()
                                .catalogValueId(x.getStatuses().iterator().next().getCatalogValueId())
                                .catalogValueVO(
                                    CatalogValueVO.builder()
                                    .catalogId(x.getStatuses().iterator().next().getCatalogValue().getCatalogId())
                                    .description(x.getStatuses().iterator().next().getCatalogValue().getDescription())
                                    .id(x.getStatuses().iterator().next().getCatalogValue().getId())
                                    .name(x.getStatuses().iterator().next().getCatalogValue().getName())
                                    .keyWord(x.getStatuses().iterator().next().getCatalogValue().getKeyWord())
                                    .build()
                                )
                                .id(x.getStatuses().iterator().next().getCatalogValueId())
                                .build()
                            )
                        )
                    ).build()
                ).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionVO findExecutionById(Long id) {
        Execution ex = executionRepository.findExecutionById(id);
        return executionToVO(ex);
    }

    private ExecutionVO executionToVO (Execution ex) {
        return ExecutionVO
                .builder()
                .alias(ex.getAlias())
                .id(ex.getId())
                .processId(ex.getProcessId())
                .statuses(transformToVO(ex))
                .finishDate(ex.getFinishDate())
                .createAt(ex.getCreatedAt())
                .build();
    }

    private Set<StatusVO> transformToVO(Execution ex) {
        return ex.getStatuses().stream()
        .map(
            sts -> StatusVO.builder()
                .id(sts.getId())
                .catalogValueVO(
                    CatalogValueVO.builder()
                        .id(sts.getCatalogValue().getId())
                        .keyWord(sts.getCatalogValue().getKeyWord())
                        .name(sts.getCatalogValue().getName())
                        .description(sts.getCatalogValue().getName())
                        .catalogId(sts.getCatalogValue().getCatalogId())
                        .build()
                ).conclusions(
                    sts.getConclusions().stream().map( cc ->
                        ConclusionsVO.builder()
                            .description(cc.getDescription())
                            .id(cc.getId())
                            .statusId(cc.getStatusId())
                            .build()
                    ).collect(Collectors.toSet())
                ).users(
                    sts.getStatusUsers().stream()
                    .map(StatusUser::getUser)
                    .map( u ->
                        SignUpRequest.builder()
                            .email(u.getEmail())
                            .firstName(u.getFirstName())
                            .lastName(u.getLastName())
                            .photo(u.getPhoto())
                            .userName(u.getUserName())
                            .build()
                    ).collect(Collectors.toSet())
                ).objectives(
                    sts.getObjectives().stream().map( obj ->
                        ObjectivesVO.builder()
                            .description(obj.getDescription())
                            .finishDate(obj.getFinishDate())
                            .id(obj.getId())
                            .build()
                    ).collect(Collectors.toSet())
                ).observations(
                    sts.getStatusUsers().stream()
                    .flatMap((StatusUser su) -> su.getObservations().stream())
                    .map( obs ->
                        ObservationsVO.builder()
                            .catalogValueVO(
                                CatalogValueVO.builder()
                                    .catalogId(obs.getCatalogValue().getCatalogId())
                                    .description(obs.getCatalogValue().getDescription())
                                    .endDate(obs.getCatalogValue().getEndDate())
                                    .keyWord(obs.getCatalogValue().getKeyWord())
                                    .id(obs.getCatalogValue().getId())
                                    .build()
                            ).description(obs.getDescription())
                            .id(obs.getId())
                            .build()
                    ).collect(Collectors.toSet())
                )
                .build()
        ).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteExecutionById(Long id) {
        try {
            Execution ex = executionRepository.findExecutionById(id);
            executionRepository.save(ex.toBuilder().enabled(Boolean.FALSE).build());
            ex.getStatuses().forEach(e -> {
                observationsRepository.saveAll(
                    e.getStatusUsers().stream()
                        .flatMap((StatusUser su) -> su.getObservations().stream())
                        .map(ob -> ob.toBuilder().enabled(Boolean.FALSE).build()).collect(Collectors.toSet())
                );
                conclusionsRepository.saveAll(
                    e.getConclusions().stream().map(cc -> cc.toBuilder().enabled(Boolean.FALSE).build()).collect(Collectors.toSet())
                );
                objectivesRepository.saveAll(
                    e.getObjectives().stream().map(obj -> obj.toBuilder().enabled(Boolean.FALSE).build()).collect(Collectors.toSet())
                );
                statusRepository.save(e.toBuilder().enabled(Boolean.FALSE).build());
            });
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
    public void deleteExecutionByProcessId(Long processId) {
        executionRepository.findExecutionByProcessId(processId).forEach(execution -> deleteExecutionById(execution.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutionVO update(ExecutionVO execution) {
        try {
            Optional<Execution> ex = executionRepository.findById(execution.getId());
            if (ex.isPresent()) {
                executionRepository.save(
                    ex.get().toBuilder()
                        .finishDate(execution.getFinishDate())
                        .alias(execution.getAlias())
                        .build()
                );
                return execution;
            } else {
                throw new AppException(ExecutionError.NOT_EXIST.getConstant());
            }
        } catch (AppException aex) {
            throw aex;
        } catch (Exception ex) {
            logger.error(LoggerError.UPDATE.getConstant(), ex);
            throw new ErrorException(CrudError.UPDATE.getConstant());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ExecutionVO> findExecutionByDate(Date dateIni, Date dateEnd) {
        Set<Execution> executions = executionRepository.findExecutionsByCreatedAtBetween(dateIni, dateEnd == null ? dateIni : dateEnd);
        Set<ExecutionVO> executionsVO = new HashSet<>();
        executions.forEach(ex->executionsVO.add(executionToVO(ex)));
        return executionsVO;
    }
}
