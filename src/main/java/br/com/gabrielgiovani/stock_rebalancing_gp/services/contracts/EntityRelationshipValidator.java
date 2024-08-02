package br.com.gabrielgiovani.stock_rebalancing_gp.services.contracts;

public interface EntityRelationshipValidator {

    void validateEntityRelationshipForInsertOrUpdateOrDelete(String username, Object object);
}