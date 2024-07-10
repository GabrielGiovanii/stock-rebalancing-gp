package br.com.gabrielgiovani.stock_rebalancing_gp.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CRUDService<T> {

    List<T> findAll();

    List<T> findByFilters(Map<String, Object> filters);

    Optional<T> findById(Integer id);

    T insertOrUpdate(T entity);

    void deleteById(Integer id);

    void saveAll(List<T> entities);
}
