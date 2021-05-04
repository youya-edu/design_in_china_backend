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

  private long id;
  private BigDecimal price;
  private AtomicLong stock;
  private Composition composition;
}
