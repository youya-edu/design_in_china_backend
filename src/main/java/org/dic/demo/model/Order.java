package org.dic.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Order {
    private long id;
    private User customer;
    private Date orderDate;
    private Date requireDate;
    private Date shippedDate;
    private String comment;
    private OrderStatus status;

    public enum OrderStatus {
        PREPARING, SENT, DONE
    }
}
