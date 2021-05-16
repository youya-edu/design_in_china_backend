package org.dic.demo.cart.service;

import lombok.AllArgsConstructor;
import org.dic.demo.cart.model.Cart;
import org.dic.demo.cart.repository.CartRepository;
import org.dic.demo.security.SecurityGuard;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartService {

  private final CartRepository cartRepository;

  public Cart getCartByUsername(String username) {
    SecurityGuard.checkUserPermission(username);
    return cartRepository.getCartByUsername(username);
  }

  public void addProductToCart(String username, long productId, int quantity) {
    SecurityGuard.checkUserPermission(username);
    cartRepository.addProductToCart(username, productId, quantity);
  }
}
