package org.alten.carrefour.products.management.backend.repositories;

import org.alten.carrefour.products.management.backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findOneByCodeOrName(String code, String name);

}
