package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.OrderRequest;
import com.example.demo.model.Inventory;
import com.example.demo.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public boolean isInStock(String skuCode, Integer quantity) {
		return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
	}

	@Transactional(readOnly = true)
	public boolean updateStock(String skuCode, Integer quantity) {
		Inventory inventory = inventoryRepository.findBySkuCode(skuCode);

		if (inventory != null && inventory.getQuantity() >= quantity) {
			inventory.setQuantity(inventory.getQuantity() - quantity);
			inventoryRepository.save(inventory);
			return true;
		}

		return false;
	}

	public void editStock(String skuCode, Integer quantity) {
		Inventory inventory = inventoryRepository.findBySkuCode(skuCode);

		if (inventory != null) {
			inventory.setQuantity(quantity);
			inventoryRepository.save(inventory);

		}

	}
	
	public void insertStock(String skuCode, Integer quantity) {
		Inventory inventory = new Inventory(skuCode, quantity);
		inventoryRepository.save(inventory);

	}
	@KafkaListener(id = "inventoryGroup", topics = "inventory")
	public void listen(OrderRequest orderRequest) {
		System.out.println("listened...");
		updateStock(orderRequest.skuCode(), orderRequest.quantity());
	}
}