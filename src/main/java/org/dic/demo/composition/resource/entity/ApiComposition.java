package org.dic.demo.composition.resource.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.composition.model.Composition;

@Getter
@Setter
@Builder(toBuilder = true)
public class ApiComposition {
  private long id;
  private String author;
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

  public static ApiComposition fromDomainObject(Composition composition) {
    return ApiComposition.builder()
        .id(composition.getId())
        .author(composition.getAuthor() != null ? composition.getAuthor().getUsername() : "佚名")
        .name(composition.getName())
        .description(composition.getDescription())
        .image(composition.getImage())
        .likes(composition.getLikes().get())
        .viewed(composition.getViewed().get())
        .status(composition.getStatus().name())
        .createdAt(composition.getCreatedAt())
        .lastModified(composition.getLastModified())
        .issuedAt(composition.getIssuedAt())
        .forSale(composition.isForSale())
        .price(composition.getPrice().setScale(2, RoundingMode.UP))
        .stock(composition.getStock())
        .build();
  }
}
