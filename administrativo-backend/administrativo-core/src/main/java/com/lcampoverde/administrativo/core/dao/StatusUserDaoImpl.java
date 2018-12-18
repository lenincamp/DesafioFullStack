package com.lcampoverde.administrativo.core.dao;

import com.lcampoverde.administrativo.cliente.dao.StatusUserDao;
import com.lcampoverde.administrativo.cliente.model.StatusUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.lcampoverde.administrativo.cliente.constant.ProjectionProperty.*;

@Repository
@Lazy
public class StatusUserDaoImpl implements StatusUserDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<StatusUser> findByUserId(Long userId) {
        return getRestrictions(userId, true);
    }

    @Override
    public List<StatusUser> findByProcessId(Long processId) {
        return getRestrictions(processId, false);
    }

    private List<StatusUser> getRestrictions(Long id, boolean condition) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<StatusUser> criteria = builder.createQuery(StatusUser.class);
        Root<StatusUser> euRoot = criteria.from(StatusUser.class);
        euRoot.fetch(PROJECTION_PROPERTY_STATUS.getConstant())
                .fetch(PROJECTION_PROPERTY_EXECUTION.getConstant())
                .fetch(PROJECTION_PROPERTY_PROCESS.getConstant())
                .fetch(PROJECTION_PROPERTY_CATALOG_VALUE.getConstant());
        euRoot.fetch(PROJECTION_PROPERTY_STATUS.getConstant()).fetch(PROJECTION_PROPERTY_CATALOG_VALUE.getConstant());
        euRoot.fetch(PROJECTION_PROPERTY_USER.getConstant());
        euRoot.fetch(PROJECTION_PROPERTY_OBSERVATION.getConstant(), JoinType.LEFT)
                .fetch(PROJECTION_PROPERTY_CATALOG_VALUE.getConstant(), JoinType.LEFT);
        criteria.select(euRoot)
                .distinct(true)
                .where(
                        builder.and(
                                builder.isTrue(
                                        euRoot.get(PROJECTION_PROPERTY_STATUS.getConstant())
                                                .get(PROJECTION_PROPERTY_EXECUTION.getConstant())
                                                .get(PROJECTION_PROPERTY_PROCESS.getConstant())
                                                .get(PROJECTION_PROPERTY_ENABLED.getConstant())
                                ),
                                builder.isTrue(
                                        euRoot.get(PROJECTION_PROPERTY_STATUS.getConstant())
                                                .get(PROJECTION_PROPERTY_EXECUTION.getConstant())
                                                .get(PROJECTION_PROPERTY_ENABLED.getConstant())
                                ),
                                builder.equal(
                                        condition ?
                                        euRoot.get(PROJECTION_PROPERTY_USER.getConstant())
                                                .get(PROJECTION_PROPERTY_ID.getConstant()) :
                                        euRoot.get(PROJECTION_PROPERTY_STATUS.getConstant())
                                                .get(PROJECTION_PROPERTY_EXECUTION.getConstant())
                                                .get(PROJECTION_PROPERTY_PROCESS.getConstant())
                                                .get(PROJECTION_PROPERTY_ID.getConstant()), id
                                )
                        )
                )
        ;
        return em.createQuery(criteria).getResultList();
    }
}
