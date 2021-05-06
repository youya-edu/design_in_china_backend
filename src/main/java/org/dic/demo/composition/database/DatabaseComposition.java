package org.dic.demo.composition.database;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.CompositionStatus;
import org.dic.demo.user.model.User;

@Setter
@Getter
@Builder(toBuilder = true)
public class DatabaseComposition {

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

  public static Composition asDomainObject(DatabaseComposition databaseComposition, User author) {
    return Composition.builder()
        .id(databaseComposition.id)
        .author(author)
        .name(databaseComposition.name)
        .description(databaseComposition.description)
        .image(databaseComposition.image)
        .likes(new AtomicLong(databaseComposition.likes))
        .viewed(new AtomicLong(databaseComposition.viewed))
        .status(CompositionStatus.from(databaseComposition.status))
        .createdAt(databaseComposition.createdAt)
        .lastModified(databaseComposition.lastModified)
        .issuedAt(databaseComposition.issuedAt)
        .forSale(databaseComposition.forSale)
        .build();
  }

  public static DatabaseComposition fromDomainObject(Composition composition) {
    return DatabaseComposition.builder()
        .id(composition.getId())
        .authorId(composition.getAuthor().getId())
        .name(composition.getName())
        .description(composition.getDescription())
        .image(composition.getImage())
        .likes(composition.getLikes().get())
        .viewed(composition.getViewed().get())
        .status(composition.getStatus().name())
        .createdAt(composition.getCreatedAt())
        .lastModified(composition.getCreatedAt())
        .issuedAt(composition.getIssuedAt())
        .forSale(composition.isForSale())
        .build();
  }
}
