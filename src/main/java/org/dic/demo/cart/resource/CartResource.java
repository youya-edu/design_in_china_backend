package org.dic.demo.cart.resource;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.cart.model.Cart;
import org.dic.demo.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users/{username}/cart")
public class CartResource {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<ViewCart> getCart(@PathVariable("username") String username) {
    Cart cart = cartService.getCartByUsername(username);
    return ResponseEntity.ok(
        ViewCart.builder()
            .ownerId(cart.getOwner().getId())
            .viewCartProducts(
                cart.getProducts().stream()
                    .map(
                        cartProduct ->
                            ViewCartProduct.builder()
                                .productId(cartProduct.getProduct().getComposition().getId())
                                .quantity(cartProduct.getQuantity())
                                .build())
                    .collect(Collectors.toList()))
            .build());
  }

  @PostMapping
  public ResponseEntity<Void> addProductToCart(
      @PathVariable("username") String username, @RequestBody ViewCartProduct payload) {
    cartService.addProductToCart(username, payload.getProductId(), payload.getQuantity());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
