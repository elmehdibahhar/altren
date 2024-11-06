package org.alten.carrefour.products.management.backend.exceptions;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {
        super("Product not found");
    }
}
