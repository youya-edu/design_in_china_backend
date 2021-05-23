package org.dic.demo.cart.database;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DatabaseCartItem {
  private final long ownerId;
  private final long productId;
  private int quantity;
}
