package org.dic.demo.cart.database;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Bean for providing data for development. Never use this in production...!! */
@Profile("dev")
@Component
@DependsOn({"flywayInitializer", "compositionDataProvider", "userDataProvider"})
@AllArgsConstructor
public class CartDataProvider {
  private final CartDao cartDao;

  @PostConstruct
  private void addCompositionData() {
    if (cartDao.cartExists()) {
      return;
    }

    List<DatabaseCartItem> items =
        IntStream.range(1, 501)
            .mapToObj(
                idx -> DatabaseCartItem.builder().ownerId(1).productId(idx).quantity(1).build())
            .collect(Collectors.toList());
    cartDao.addItemsToCart(items);
  }
}
