package br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CRUDService<T> {

    List<T> findAllByUsername(String username);

    List<T> findByUsernameAndFilters(String username, Map<String, Object> filters);

    Optional<T> findByUsernameAndId(String username, Integer id);

    T insertOrUpdate(String username, T entity);

    Boolean wasDeletedById(String username, Integer id);

    void saveAll(String username, List<T> entities);

    void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object);
}