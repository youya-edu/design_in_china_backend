package org.dic.demo.cart.repository;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.cart.database.CartDao;
import org.dic.demo.cart.database.DatabaseCartItem;
import org.dic.demo.cart.model.Cart;
import org.dic.demo.cart.model.CartItem;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.Product;
import org.dic.demo.composition.repository.CompositionRepository;
import org.dic.demo.user.model.User;
import org.dic.demo.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartRepository {

  private final UserRepository userRepository;
  private final CompositionRepository compositionRepository;
  private final CartDao cartDao;

  /**
   * Gets user's cart by username.
   *
   * @param username the username
   * @return the user's cart, or null if the user's cart is empty
   */
  public Cart getCartByUsername(String username) {
    User user = userRepository.getUserByUsername(username);
    List<DatabaseCartItem> databaseCartItems = cartDao.getCartByUserId(user.getId());
    List<CartItem> cartItems =
        databaseCartItems.stream()
            .map(
                databaseCartItem -> {
                  Composition composition =
                      compositionRepository.getCompositionById(databaseCartItem.getProductId());
                  return CartItem.builder()
                      .product(
                          Product.builder()
                              .composition(composition)
                              .price(composition.getPrice())
                              .build())
                      .quantity(databaseCartItem.getQuantity())
                      .build();
                })
            .collect(Collectors.toList());

    return Cart.builder().owner(user).items(cartItems).build();
  }

  public void addProductToCart(String username, long productId, int quantity) {
    User user = userRepository.getUserByUsername(username);
    cartDao.addItemToCart(
        DatabaseCartItem.builder()
            .ownerId(user.getId())
            .productId(productId)
            .quantity(quantity)
            .build());
  }
}
