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
      boolean itemFound = false;

      //Para setar um preço no itemDTO que será mostrado no retorno.
      itemdto.setUnitPrice(product.getPrice());

      if(product.getStock() == 0) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("[ERROR] Empty stock.");
      }

      for (Item obj : cart.getItemsList()) {
        //Para verificar cada item já existente no carrinho e se existir, somar com a quantidade atual.
        if (obj.getName().equals(itemdto.getName())) {
          obj.setQuantity(obj.getQuantity() + itemdto.getQuantity());

          //método que atualiza o estoque na variavel product que contem informaçoes vinda do microserviço Product.
          //Envia itemDTO para dar GET na qtd solicitada e product para dar GET e SET na variavel product.
          stockUpdate(itemdto, product);

          itemFound = true;
          break;
        }
      }

      if (!itemFound) {
        stockUpdate(itemdto, product);
        cart.addItem(modelMapper.map(itemdto, Item.class));
      }

      //Requisição PUT ao microserviço Produto.
      productFeignClient.editProduct(product);

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

    return ResponseEntity.ok("item successfully added to cart.");
  }

  private Product reqFindByNameProduct(ItemDTO itemdto) {
    return productFeignClient.findByName(itemdto.getName()).getBody();
  }
  private void stockUpdate(ItemDTO itemdto, Product product) {
    product.setStock(product.getStock() - itemdto.getQuantity());
  }
}
