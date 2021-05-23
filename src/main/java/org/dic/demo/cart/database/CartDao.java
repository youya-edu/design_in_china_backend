package org.dic.demo.cart.database;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CartDao {

  boolean cartExists();

  List<DatabaseCartItem> getCartByUserId(long userId);

  long addItemToCart(DatabaseCartItem cartItem);

  int addItemsToCart(List<DatabaseCartItem> cartItems);
}
