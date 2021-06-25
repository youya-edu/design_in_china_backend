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

  public Cart getCart() {
    return cartRepository.getCartByUsername(SecurityGuard.getCurrentUsername());
  }

  public void addProductToCart(long productId, int quantity) {
    cartRepository.addProductToCart(SecurityGuard.getCurrentUsername(), productId, quantity);
  }
}
