package org.alten.carrefour.products.management.backend.services;

import org.alten.carrefour.products.management.backend.dtos.ProductDto;
import org.alten.carrefour.products.management.backend.exceptions.ProductAlreadyExistsException;
import org.alten.carrefour.products.management.backend.exceptions.ProductNotFoundException;
import org.alten.carrefour.products.management.backend.models.Product;
import org.alten.carrefour.products.management.backend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * retrieve all products
     *
     * @return List<ProductDto>
     */
    public List<ProductDto> retrieveAll() {
        ModelMapper modelMapper = new ModelMapper();
        return productRepository.findAll().stream().map(user -> modelMapper.map(user, ProductDto.class)).collect(Collectors.toList());
    }

    /**
     * retrieve a product by its id
     *
     * @param id product id
     * @return ProductDto
     * @throws ProductNotFoundException
     */
    public ProductDto retrieve(Long id) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(productRepository.findById(id).orElseThrow(ProductNotFoundException::new), ProductDto.class);
    }

    /**
     * create a new product
     *
     * @param productDto the data product to create
     * @return the created ProductDto
     * @throws ProductAlreadyExistsException
     */
    public ProductDto create(ProductDto productDto) throws ProductAlreadyExistsException {
        ModelMapper modelMapper = new ModelMapper();
        // get Product from database
        if (productDto.getId() != null || productRepository.findOneByCodeOrName(productDto.getCode(), productDto.getName()).isPresent()) {
            throw new ProductAlreadyExistsException();
        }

        // create Product model from dto
        productDto.setCreatedAt(LocalDateTime.now());
        Product product = productRepository.save(modelMapper.map(productDto, Product.class));
        return modelMapper.map(product, ProductDto.class);
    }

    /**
     * update a product by its id
     *
     * @param id         product id
     * @param productDto the updated data product
     * @return last updated ProductDto
     * @throws ProductNotFoundException
     */
    public ProductDto update(Long id, ProductDto productDto) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        // get Product from database
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        // set dto data into
        modelMapper.map(productDto, product);
        product.setId(id);

        // update product
        productDto.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return retrieve(id);
    }

    /**
     * remove a product from database
     *
     * @param id product id
     * @return true is the product deleted
     * @throws ProductNotFoundException
     */
    public Boolean delete(Long id) throws ProductNotFoundException {
        // get Product from database
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        // delete product from database
        productRepository.delete(product);

        return productRepository.findById(id).isEmpty();
    }

}
