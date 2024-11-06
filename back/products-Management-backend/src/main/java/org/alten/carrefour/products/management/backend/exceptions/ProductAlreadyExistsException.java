package org.alten.carrefour.products.management.backend.exceptions;

public class ProductAlreadyExistsException extends Exception {

    public ProductAlreadyExistsException() {
        super("Product is already exists");
    }
}
