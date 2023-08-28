package com.gabriel.mscart.controllers;

import com.gabriel.mscart.models.entities.Item;
import com.gabriel.mscart.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService service;

  @GetMapping
  public List<Item> getCart() {
    return service.getListItems();
  }

  @GetMapping("/total")
  public double totalPrice() {
    return service.totalPrice();
  }

  @PostMapping
  public void addItem(@RequestBody Item item) {
    service.addItems(item);
  }
}
