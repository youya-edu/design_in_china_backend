package org.dic.demo.cart.resource;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViewCartItem {
  private final long productId;
  private final String name;
  private final String description;
  private final String image;
  private final BigDecimal price;
  private final int quantity;
}
