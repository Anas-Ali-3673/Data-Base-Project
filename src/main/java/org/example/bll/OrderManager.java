package org.example.bll;

import org.example.dal.OrderDal;
import org.example.dto.Order;
import org.example.dto.OrderItem;
import org.example.dto.OrderStatus;

import java.util.List;

public class OrderManager {
    private final OrderDal orderDal;

    public OrderManager() {
        this.orderDal = new OrderDal();
    }

    public boolean placeOrder(Order order) {
        return orderDal.saveOrder(order);
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDal.getOrdersByUserId(userId);
    }

    public boolean updateOrderStatus(int orderId, OrderStatus status) {
        return orderDal.updateOrderStatus(orderId, status);
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderDal.getOrderItemsByOrderId(orderId);
    }
}