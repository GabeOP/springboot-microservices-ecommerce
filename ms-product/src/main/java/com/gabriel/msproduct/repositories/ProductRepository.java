package com.gabriel.msproduct.repositories;

import com.gabriel.msproduct.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findByName(String name);
}
