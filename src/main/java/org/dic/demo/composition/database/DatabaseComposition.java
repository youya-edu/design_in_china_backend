package org.dic.demo.composition.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.common.TransformableToDomain;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.CompositionStatus;
import org.dic.demo.composition.model.Product;

@Setter
@Getter
@Builder(toBuilder = true)
public class DatabaseComposition implements TransformableToDomain<Composition> {

  private long id;
  private long authorId;
  private String name;
  private String description;
  private String image;
  private long likes;
  private long viewed;
  private String status;
  private Date createdAt;
  private Date lastModified;
  private Date issuedAt;
  private boolean forSale;
  private BigDecimal price;
  private long stock;

  @Override
  public Composition toDomainObject() {
    return Composition.builder()
        .id(this.id)
        .name(this.name)
        .description(this.description)
        .image(this.image)
        .likes(new AtomicLong(this.likes))
        .viewed(new AtomicLong(this.viewed))
        .status(CompositionStatus.from(this.status))
        .createdAt(this.createdAt)
        .lastModified(this.lastModified)
        .issuedAt(this.issuedAt)
        .forSale(this.forSale)
        .product(
            Product.builder().price(this.getPrice()).stock(new AtomicLong(this.getStock())).build())
        .build();
  }
}
