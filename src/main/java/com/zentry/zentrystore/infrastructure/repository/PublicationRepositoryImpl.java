package com.zentry.zentrystore.infrastructure.repository;

import com.zentry.zentrystore.domain.publication.model.Publication;
import com.zentry.zentrystore.domain.publication.repository.PublicationRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PublicationRepositoryImpl implements PublicationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Publication> searchAdvanced(String searchTerm, Long categoryId, BigDecimal minPrice,
                                            BigDecimal maxPrice, String city, String condition,
                                            Boolean freeShipping, String sortBy, String order) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Publication> query = cb.createQuery(Publication.class);
        Root<Publication> root = query.from(Publication.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isBlank()) {
            Predicate titleLike = cb.like(cb.lower(root.get("title")), "%" + searchTerm.toLowerCase() + "%");
            Predicate descLike = cb.like(cb.lower(root.get("description")), "%" + searchTerm.toLowerCase() + "%");
            predicates.add(cb.or(titleLike, descLike));
        }

        if (categoryId != null) predicates.add(cb.equal(root.get("categoryId"), categoryId));
        if (minPrice != null) predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minPrice));
        if (maxPrice != null) predicates.add(cb.lessThanOrEqualTo(root.get("amount"), maxPrice));
        if (city != null && !city.isBlank()) predicates.add(cb.equal(root.get("city"), city));
        if (condition != null && !condition.isBlank()) predicates.add(cb.equal(root.get("condition"), condition));
        if (freeShipping != null) predicates.add(cb.equal(root.get("allowsShipping"), freeShipping));

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        if (sortBy != null && order != null) {
            Path<?> sortPath = root.get(sortBy);
            query.orderBy(order.equalsIgnoreCase("desc") ? cb.desc(sortPath) : cb.asc(sortPath));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
