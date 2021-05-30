package org.dic.demo.cart.resource;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.dic.demo.cart.model.Cart;
import org.dic.demo.cart.service.CartService;
import org.dic.demo.common.PaginationParam;
import org.dic.demo.composition.model.Composition;
import org.dic.demo.composition.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartResource {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<ViewCart> getCart(PaginationParam paginationParam) {
    Cart cart = cartService.getCart(paginationParam);
    return ResponseEntity.ok(
        ViewCart.builder()
            .ownerId(cart.getOwner().getId())
            .items(
                cart.getItems().stream()
                    .map(
                        cartItem -> {
                          Product product = cartItem.getProduct();
                          Composition composition = product.getComposition();
                          return ViewCartItem.builder()
                              .productId(composition.getId())
                              .name(composition.getName())
                              .description(composition.getDescription())
                              .image(composition.getImage())
                              .price(product.getPrice())
                              .quantity(cartItem.getQuantity())
                              .build();
                        })
                    .collect(Collectors.toList()))
            .build());
  }

  @PostMapping
  public ResponseEntity<Void> addProductToCart(@RequestBody ViewCartItem payload) {
    cartService.addProductToCart(payload.getProductId(), payload.getQuantity());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
