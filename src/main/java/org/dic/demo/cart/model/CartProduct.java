package org.dic.demo.cart.model;

import lombok.Builder;
import lombok.Getter;
import org.dic.demo.composition.model.Product;

@Getter
@Builder
public class CartProduct {

  private final Product product;
  private final int quantity;
}
