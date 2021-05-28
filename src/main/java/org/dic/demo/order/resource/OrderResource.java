package org.dic.demo.order.resource;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dic.demo.order.model.Order;
import org.dic.demo.order.service.OrderService;
import org.dic.demo.util.web.WebUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderResource {

  private final OrderService orderService;

  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> getOrder(@PathVariable("orderId") long orderId) {
    return ResponseEntity.ok(orderService.getOrderById(orderId));
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order payload, HttpServletRequest req) {
    Order newOrder = orderService.createOrder(payload);
    return ResponseEntity.created(WebUtils.addPathToUri(req, String.valueOf(newOrder.getId())))
        .body(newOrder);
  }

  @PutMapping
  public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
    return ResponseEntity.ok(orderService.updateOrder(order));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") long orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity.noContent().build();
  }
}
