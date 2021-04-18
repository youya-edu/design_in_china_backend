package org.dic.demo.composition.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.user.model.User;

@Setter
@Getter
public class Comment {

  private long id;
  private User author;
  private Composition composition;
  private String content;
  private Date createdAt;
  private Date lastModified;
  private AtomicLong likes;
  private AtomicLong dislikes;

}
