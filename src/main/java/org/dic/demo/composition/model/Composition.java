package org.dic.demo.composition.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.user.model.User;

@Setter
@Getter
@Builder(toBuilder = true)
public class Composition {

  private long id;
  private User author;
  private String name;
  private String description;
  private String image;
  private AtomicLong likes;
  private AtomicLong viewed;
  private CompositionStatus status;
  private Date createdAt;
  private Date lastModified;
  private Date issuedAt;
  private boolean forSale;
  private Product product;

  public BigDecimal getPrice() {
    return this.product.getPrice();
  }

  public long getStock() {
    return this.product.getStock().get();
  }
}
