package br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts;

public interface EntityCreationService<T, G> {

    T createEntity(G dto);
}