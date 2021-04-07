package org.dic.demo.repository;

import org.dic.demo.model.Order;
import org.dic.demo.repository.servicestub.OrderServiceStub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    private final OrderServiceStub orderServiceStub;

    public OrderRepository(OrderServiceStub orderServiceStub) {
        this.orderServiceStub = orderServiceStub;
    }

    public Order getOrderById(long orderId) {
        return orderServiceStub.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderServiceStub.getAllOrders();
    }

    public Order createOrder(Order order) {
        return orderServiceStub.createOrder(order);
    }

    public Order updateOrder(Order order) {
        return orderServiceStub.updateOrder(order);
    }

    public void deleteOrder(long orderId) {
        orderServiceStub.deleteOrder(orderId);
    }
}
