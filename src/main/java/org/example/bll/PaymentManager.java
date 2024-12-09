package org.example.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.example.dal.PaymentDal;
import org.example.dal.ShoppingCart;
import org.example.dto.Order;
import org.example.dto.OrderItem;
import org.example.dto.OrderStatus;
import org.example.dto.PaymentDto;
import org.example.dto.ProductDto;

public class PaymentManager {
    private final PaymentDal paymentDal;
    private final OrderManager orderManager;

    public PaymentManager() {
        this.paymentDal = new PaymentDal();
        this.orderManager = new OrderManager();
    }

    public boolean isUserExists(int userId) {
        return paymentDal.isUserExists(userId);
    }

    public void savePaymentToDatabase(PaymentDto paymentDto) {
        paymentDal.savePaymentToDatabase(paymentDto);
    }

    public boolean placeOrder(int userId, ShoppingCart shoppingCart) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Entry<ProductDto, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            ProductDto product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            orderItems.add(new OrderItem(product.getId(), quantity, price));
        }

        Order order = new Order(userId, new Date(), shoppingCart.getTotalCost(), orderItems, OrderStatus.PROCESSING);
        return orderManager.placeOrder(order);
    }
}