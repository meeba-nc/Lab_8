package ru.ncedu.course.catalog_example.model.dao;

import ru.ncedu.course.catalog_example.model.entity.CommentEntity;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class CommentDAO extends AbstractDAO<CommentEntity, Long> {

    private static final String FIND_BY_OFFERING_ID = "FROM " + CommentEntity.class.getName() + " comment WHERE comment.offering.id = :offeringId";

    public CommentDAO() {
        init(CommentEntity.class);
    }

    public List<CommentEntity> findByOfferingId(long offeringId) {
        Map<String, Object> params = new HashMap<>();
        params.put("offeringId", offeringId);

        return customFindListQuery(FIND_BY_OFFERING_ID, params);
    }

}
