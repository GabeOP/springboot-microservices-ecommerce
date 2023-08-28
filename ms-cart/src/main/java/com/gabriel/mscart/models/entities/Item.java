package com.gabriel.mscart.models.entities;

public class Item {

  private String name;
  private double unitPrice;
  private int quantity;
  public Item() {}
  public Item(String name, double unitPrice, int quantity) {
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
}
