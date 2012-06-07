package com.force.example.fulfillment.order.service;

import com.force.example.fulfillment.order.model.Order;

import java.util.List;

public interface OrderService {
    public void addOrder(Order order);
    public List<Order> listOrders();
    public Order findOrder(Integer orderId);
    public void removeOrder(Integer orderId);
	List<Order> findOrderById(String id);
}