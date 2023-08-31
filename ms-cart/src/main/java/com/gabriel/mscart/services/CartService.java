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
    Product product = reqFindByNameProduct(itemdto);

    try {
      //Para setar um preço no itemDTO que será mostrado no retorno.
      itemdto.setUnitPrice(product.getPrice());

      if(product.getStock() == 0 || itemdto.getQuantity() > product.getStock()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[ERROR] Insufficient stock for the requested quantity.");
      }

      Item check = checkIfExistsItemInCartByName(itemdto);
      if(check == null) {
        cart.addItem(modelMapper.map(itemdto, Item.class));
      }

      if(check != null) {
        check.setQuantity(check.getQuantity() + itemdto.getQuantity());
      }

      //Chama o método que dá update na variável product.
      subtractFromStockWhenAddItemToCart(itemdto, product);

      //E finalmente a requisição PUT ao microserviço Produto.
      productFeignClient.editProduct(product);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return ResponseEntity.ok("item successfully added to cart.");
  }

  public void deleteItem(ItemDTO itemdto) {
    Product product = reqFindByNameProduct(itemdto);
    Item check = checkIfExistsItemInCartByName(itemdto);

    if(check != null) {
      cart.deleteItem(modelMapper.map(itemdto, Item.class));
      sumToStockWhenRemoveItemFromCart(itemdto, product);
    }

    productFeignClient.editProduct(product);
  }

  private Item checkIfExistsItemInCartByName(ItemDTO itemdto) {
    Item itemFound = null;

    for (Item obj : cart.getItemsList()) {
      if (obj.getName().equals(itemdto.getName())) {
        itemFound = obj;
        break;
      }
    }
    return itemFound;
  }

  private Product reqFindByNameProduct(ItemDTO itemdto) {
    return productFeignClient.findByName(itemdto.getName()).getBody();
  }

  private void subtractFromStockWhenAddItemToCart (ItemDTO itemdto, Product product) {
    product.setStock(product.getStock() - itemdto.getQuantity());
  }
  private void sumToStockWhenRemoveItemFromCart (ItemDTO itemdto, Product product) {
    product.setStock(product.getStock() + itemdto.getQuantity());
  }
}
