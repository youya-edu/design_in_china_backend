package org.dic.demo.cart.resource;

import java.util.List;
import lombok.Builder;

@Builder
public class ViewCart {
  private final long ownerId;
  private final List<ViewCartProduct> products;
}
