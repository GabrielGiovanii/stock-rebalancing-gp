package br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CRUDService<T, ID> {

    List<T> findAllByUsername(String username);

    List<T> findByUsernameAndFilters(String username, Map<String, Object> filters);

    Optional<T> findByUsernameAndId(String username, ID id);

    T insertOrUpdate(String username, T entity);

    Boolean wasDeletedById(String username, ID id);
}