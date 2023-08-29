package com.gabriel.mscart.models.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

  public List<String> getNameItem() {
    return itemsList.stream().map(n -> n.getName()).collect(Collectors.toList());
  }

  public void setItemQuantity(Item item) {
    Item itemName = (Item) itemsList.stream().filter(x -> x.getName().equals(item.getName()));
    itemName.setQuantity(item.getQuantity());
  }

  public void addItem(Item item) {
    itemsList.add(item);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cart cart = (Cart) o;
    return Objects.equals(id, cart.id) && Objects.equals(itemsList, cart.itemsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemsList);
  }
}
