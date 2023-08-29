package com.gabriel.mscart.dto;

import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Objects;

public class ItemDTO {
  private String name;
  @ReadOnlyProperty
  private double unitPrice;
  private int quantity;

  public ItemDTO() {
  }

  public ItemDTO(String name, double unitPrice, int quantity) {
    this.name = name;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemDTO itemDTO = (ItemDTO) o;
    return Objects.equals(name, itemDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}