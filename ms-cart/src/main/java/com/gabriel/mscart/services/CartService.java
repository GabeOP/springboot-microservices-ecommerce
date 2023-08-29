package com.gabriel.mscart.services;

import com.gabriel.mscart.dto.ItemDTO;
import com.gabriel.mscart.feignclients.ProductFeignClient;
import com.gabriel.mscart.models.Product;
import com.gabriel.mscart.models.entities.Cart;
import com.gabriel.mscart.models.entities.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  private Cart cart;

  @Autowired
  private ProductFeignClient productFeignClient;
  public List<Item> getListItems() {
    return cart.getItemsList();
  }

  public double totalPrice() {
    return cart.getItemsList().stream()
            .map(obj -> obj.getUnitPrice() * obj.getQuantity())
            .reduce(0.0, (x,y) -> x + y);
  }

  public ResponseEntity<String> addItems(ItemDTO itemdto) {
    Product product = productFeignClient.findByName(itemdto.getName()).getBody();

    try {
      boolean itemFound = false;
      itemdto.setUnitPrice(product.getPrice());

      if(product.getStock() == 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[ERROR] Empty stock.");
      }

      for (Item obj : cart.getItemsList()) {
        if (obj.getName().equals(itemdto.getName())) {
          obj.setQuantity(obj.getQuantity() + itemdto.getQuantity());
          product.setStock(product.getStock() - itemdto.getQuantity());
          itemdto.setUnitPrice(product.getPrice());
          itemFound = true;
          break;
        }
      }

      if (!itemFound) {
        product.setStock(product.getStock() - itemdto.getQuantity());
        cart.addItem(modelMapper.map(itemdto, Item.class));
      }


      productFeignClient.editProduct(product);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return ResponseEntity.ok("item successfully added to cart.");
  }

}
