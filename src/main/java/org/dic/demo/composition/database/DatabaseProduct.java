package org.dic.demo.composition.database;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.Product;

@Setter
@Getter
@Builder(toBuilder = true)
public class DatabaseProduct {

  private long id;
  private BigDecimal price;
  private long stock;
  private long compositionId;

  public static Product asDomainObject(DatabaseProduct databaseProduct, Composition composition) {
    return Product.builder()
        .id(databaseProduct.id)
        .price(databaseProduct.price)
        .stock(new AtomicLong(databaseProduct.stock))
        .composition(composition)
        .build();
  }

  public static DatabaseProduct fromDomainObject(Product product, Composition composition) {
    return DatabaseProduct.builder()
        .id(product.getId())
        .price(product.getPrice())
        .stock(product.getStock().get())
        .compositionId(composition.getId())
        .build();
  }
}
