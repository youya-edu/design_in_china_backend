package org.dic.demo.order.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.dic.demo.user.model.User;

@Setter
@Getter
public class Order {

  private long id;
  private User customer;
  private Date orderDate;
  private Date requireDate;
  private Date shippedDate;
  private String memo;
  private OrderStatus status;

  public enum OrderStatus {
    PREPARING, SENT, DONE
  }
}
