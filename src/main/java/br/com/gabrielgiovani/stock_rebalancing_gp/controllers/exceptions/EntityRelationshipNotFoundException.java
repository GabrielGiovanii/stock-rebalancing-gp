package br.com.gabrielgiovani.stock_rebalancing_gp.controllers.exceptions;

public class EntityRelationshipNotFoundException extends RuntimeException {

    public EntityRelationshipNotFoundException() {
    }

    public EntityRelationshipNotFoundException(String message) {
        super(message);
    }
}