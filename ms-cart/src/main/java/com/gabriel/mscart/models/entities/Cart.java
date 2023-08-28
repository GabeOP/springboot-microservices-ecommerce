package com.gabriel.mscart.models.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {

  private Long id;
  private List<Item> itemsList;

  public Cart() {
    this.itemsList = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Item> getItemsList() {
    return itemsList;
  }

  public void addItem(Item item) {
    itemsList.add(item);
  }
}
