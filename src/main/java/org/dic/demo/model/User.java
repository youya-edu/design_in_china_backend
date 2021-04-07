package org.dic.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class User {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String description;
    private Date createdAt;
    private List<Long> compositions = new ArrayList<>();
    private List<Long> orders = new ArrayList<>();
    private List<Long> followed = new ArrayList<>();
    private List<Long> following = new ArrayList<>();

    public User() {
        this.createdAt = new Date();
    }
}
