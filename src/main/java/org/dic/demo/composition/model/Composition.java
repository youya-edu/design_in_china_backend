package org.dic.demo.composition.model;

import lombok.Getter;
import lombok.Setter;
import org.dic.demo.user.model.User;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
public class Composition {

    private long id;
    private User author;
    private String name;
    private String description;
    private Date createdAt;
    private Date lastModified;
    private List<Comment> comments;
    private AtomicLong heat;
    private AtomicLong likes;
    private AtomicLong dislikes;
    private String image;
    private long stock;
    private double price;
}
