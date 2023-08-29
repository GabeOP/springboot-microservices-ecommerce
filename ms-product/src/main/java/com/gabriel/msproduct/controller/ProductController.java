package com.gabriel.msproduct.controller;

import com.gabriel.msproduct.entities.Product;
import com.gabriel.msproduct.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ProductService service;

  @GetMapping
  public ResponseEntity<List<Product>> findAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(service.findAll());
  }

  @GetMapping(value = "/{name}")
  public ResponseEntity<Product> findByName(@Validated @PathVariable String name) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(service.findByName(name));
  }

  @PostMapping
  public ResponseEntity<String> createProduct(@RequestBody Product obj) {
    service.createProduct(obj);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Product created successfully.");
  }

  @PutMapping
  public ResponseEntity<String> editProduct(@RequestBody Product obj) {
    service.editProduct(obj);
    return ResponseEntity.ok("Editado com sucesso.");
  }
}
