package com.gabriel.msproduct.services;

import com.gabriel.msproduct.entities.Product;
import com.gabriel.msproduct.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

  @Autowired
  ProductRepository repository;

  public List<Product> findAll() {
    return repository.findAll();
  }

  public Product findByName(String name) {
      return repository.findByName(name);
  }

  public void createProduct(Product product) {
    repository.save(product);
  }

  public void editProduct(Product obj) {
    Product product = repository.findByName(obj.getName());
    try{
      product.setStock(obj.getStock());
      repository.save(product);
    }catch(Exception ex) {
      System.out.println(ex.getMessage() + "Empty stock.");
    }
  }
}
