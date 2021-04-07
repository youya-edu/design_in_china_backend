package org.dic.demo.repository.servicestub;

import org.dic.demo.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class OrderServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, Order> orders = new ConcurrentHashMap<>();

    static {
        Order order1 = new Order();
        order1.setId(idCounter.incrementAndGet());
        order1.setStatus(Order.OrderStatus.PREPARING);

        Order order2 = new Order();
        order2.setId(idCounter.incrementAndGet());
        order2.setStatus(Order.OrderStatus.PREPARING);

        orders.put(order1.getId(), order1);
        orders.put(order2.getId(), order2);
    }

    public Order getOrderById(long orderId) {
        return orders.get(orderId);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order createOrder(Order order) {
        order.setId(idCounter.incrementAndGet());
        order.setStatus(Order.OrderStatus.PREPARING);
        orders.put(order.getId(), order);
        return order;
    }

    public Order updateOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public void deleteOrder(long orderId) {
        orders.remove(orderId);
    }
}
