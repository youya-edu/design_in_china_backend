package org.dic.demo.cart.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViewCartProduct {
  private final long productId;
  private final int quantity;
}
