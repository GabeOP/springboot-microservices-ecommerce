package com.gabriel.mscart.services;

import com.gabriel.mscart.feignclients.ProductFeignClient;
import com.gabriel.mscart.models.entities.Cart;
import com.gabriel.mscart.models.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

  @Autowired
  private Cart cart;

  @Autowired
  private ProductFeignClient productFeignClient;
  public List<Item> getListItems() {
    return cart.getItemsList();
  }

  public double totalPrice() {
    return cart.getItemsList().stream()
            .map(price -> price.getUnitPrice())
            .reduce(0.0, (x,y) -> x + y);
  }

  public void addItems(Item item) {

    //Product product = productFeignClient.findByName(item.getName()).getBody();

    try{
      //if(product.getName().equals(item.getName()))
        cart.addItem(item);
    }catch(NullPointerException ex){
      System.out.println(ex.getMessage());
    }

  }
}
