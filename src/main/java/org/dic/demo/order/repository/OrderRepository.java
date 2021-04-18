package org.dic.demo.order.repository;

import java.util.List;
import org.dic.demo.order.model.Order;
import org.dic.demo.order.servicestub.OrderServiceStub;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

  public Order getOrderById(long orderId) {
    return OrderServiceStub.getOrderById(orderId);
  }

  public List<Order> getAllOrders() {
    return OrderServiceStub.getAllOrders();
  }

  public Order createOrder(Order order) {
    return OrderServiceStub.createOrder(order);
  }

  public Order updateOrder(Order order) {
    return OrderServiceStub.updateOrder(order);
  }

  public void deleteOrder(long orderId) {
    OrderServiceStub.deleteOrder(orderId);
  }
}
