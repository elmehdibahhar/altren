package org.alten.carrefour.products.management.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.alten.carrefour.products.management.backend.dtos.ProductDto;
import org.alten.carrefour.products.management.backend.exceptions.ProductNotFoundException;
import org.alten.carrefour.products.management.backend.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private ResponseStatusException responseStatusException;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<ProductDto> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDto.class),
                    examples = @ExampleObject(value = "{ \"code\": \"ref_01201\", \"name\": \"produc Name\" }")))
                                             @RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.create(productDto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Get a all Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<ProductDto>> retrieveAll() {
        try {
            return ResponseEntity.ok(productService.retrieveAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Get a Product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> retrieve(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.retrieve(id));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw responseStatusException;
        }
    }

    @Operation(summary = "update a Product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     description = "Product to update", required = true,
                                                     content = @Content(mediaType = "application/json",
                                                             schema = @Schema(implementation = ProductDto.class),
                                                             examples = @ExampleObject(value = "{ \"code\": \"ref_01201\", \"name\": \"produc Name\" }")))
                                             @RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.update(id, productDto));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw responseStatusException;
        }
    }

    @Operation(summary = "delete a Product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw responseStatusException;
        }
    }

}
