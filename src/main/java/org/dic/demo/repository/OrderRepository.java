package org.dic.demo.repository;

import org.dic.demo.model.Order;
import org.dic.demo.repository.servicestub.OrderServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

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
