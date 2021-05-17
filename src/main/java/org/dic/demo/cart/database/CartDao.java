package org.dic.demo.cart.database;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CartDao {

  List<DatabaseCartProduct> getCartByUserId(long userId);

  long addProductToCart(DatabaseCartProduct cartProduct);
}
