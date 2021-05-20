package org.dic.demo.composition.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.common.TransformableToDatabase;
import org.dic.demo.common.TransformableToView;
import org.dic.demo.composition.database.DatabaseComposition;
import org.dic.demo.composition.resource.ViewComposition;
import org.dic.demo.user.model.User;

@Setter
@Getter
@Builder(toBuilder = true)
public class Composition
    implements TransformableToDatabase<DatabaseComposition>, TransformableToView<ViewComposition> {

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

  @Override
  public DatabaseComposition toDatabaseObject() {
    return DatabaseComposition.builder()
        .id(this.getId())
        .authorId(this.getAuthor().getId())
        .name(this.getName())
        .description(this.getDescription())
        .image(this.getImage())
        .likes(this.getLikes().get())
        .viewed(this.getViewed().get())
        .status(this.getStatus().name())
        .createdAt(this.getCreatedAt())
        .lastModified(this.getCreatedAt())
        .issuedAt(this.getIssuedAt())
        .forSale(this.isForSale())
        .build();
  }

  @Override
  public ViewComposition toViewObject() {

    return ViewComposition.builder()
        .id(this.getId())
        .author(this.getAuthor() != null ? this.getAuthor().getUsername() : "佚名")
        .name(this.getName())
        .description(this.getDescription())
        .image(this.getImage())
        .likes(this.getLikes().get())
        .viewed(this.getViewed().get())
        .status(this.getStatus().name())
        .createdAt(this.getCreatedAt())
        .lastModified(this.getLastModified())
        .issuedAt(this.getIssuedAt())
        .forSale(this.isForSale())
        .price(this.getPrice().setScale(2, RoundingMode.UP))
        .stock(this.getStock())
        .build();
  }
}
