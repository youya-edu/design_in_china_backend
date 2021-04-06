package org.dic.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String description;
    private Date createdAt;
}
