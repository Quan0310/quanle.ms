package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.OrderRequest;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest, Long UserId) {
    	 boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
         if (inStock) {
             var order = mapToOrder(orderRequest);
             order.setUserId(UserId);
             orderRepository.save(order);
         } else {
             throw new RuntimeException("Product with Skucode " + orderRequest.skuCode() + "is not in stock");
         }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}