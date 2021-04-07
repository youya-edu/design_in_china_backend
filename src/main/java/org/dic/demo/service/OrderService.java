package org.dic.demo.service;

import org.dic.demo.model.Order;
import org.dic.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Order createOrder(Order order) {
        return orderRepository.createOrder(order);
    }

    public Order updateOrder(Order order) {
        return orderRepository.updateOrder(order);
    }

    public void deleteOrder(long orderId) {
        orderRepository.deleteOrder(orderId);
    }
}
