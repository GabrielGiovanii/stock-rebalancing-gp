package br.com.gabrielgiovani.stock_rebalancing_gp.services;

public interface EntityCreationService<T, G> {

    T createEntity(G dto);
}