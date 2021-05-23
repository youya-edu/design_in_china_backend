package org.dic.demo.cart.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dic.demo.user.model.User;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Cart {

  private User owner;
  @Default private List<CartItem> items = new ArrayList<>();
}
