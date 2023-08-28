package com.gabriel.mscart.feignclients;

import com.gabriel.mscart.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "ms-product", url= "localhost:8001", path="/product")
public interface ProductFeignClient {

  @GetMapping(value = "/{name}")
  ResponseEntity<Product> findByName(@Validated @PathVariable String name);
}
