package org.dic.demo.servicestub;

import org.dic.demo.exception.OrderNotFoundException;
import org.dic.demo.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class OrderServiceStub {

    private static final AtomicLong idCounter = new AtomicLong(-1);
    private static final Map<Long, Order> orders = new ConcurrentHashMap<>();

    public static Order getOrderById(long orderId) {
        checkOrderExistence(orderId);
        return orders.get(orderId);
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public static Order createOrder(Order order) {
        order.setId(idCounter.incrementAndGet());
        order.setStatus(Order.OrderStatus.PREPARING);
        orders.put(order.getId(), order);
        return order;
    }

    public static Order updateOrder(Order order) {
        checkOrderExistence(order.getId());
        orders.put(order.getId(), order);
        return order;
    }

    public static void deleteOrder(long orderId) {
        checkOrderExistence(orderId);
        orders.remove(orderId);
    }

    private static void checkOrderExistence(long orderId) {
        if (!orders.containsKey(orderId)) {
            throw new OrderNotFoundException("No such order: " + orderId);
        }
    }
}
