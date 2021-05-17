package org.dic.demo.composition.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder(toBuilder = true)
public class Product {

  private Composition composition;
  private BigDecimal price;
  private AtomicLong stock;
}
