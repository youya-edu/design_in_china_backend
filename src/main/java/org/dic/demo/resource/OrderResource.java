package org.dic.demo.resource;

import org.dic.demo.model.Order;
import org.dic.demo.service.OrderService;
import org.dic.demo.util.HttpUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(
        value = "/orders",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
)
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
        return ResponseEntity.created(HttpUtils.uriWithPath(req, String.valueOf(newOrder.getId())))
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
