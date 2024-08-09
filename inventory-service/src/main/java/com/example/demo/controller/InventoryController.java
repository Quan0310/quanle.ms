package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
		return inventoryService.isInStock(skuCode, quantity);
	}
	
	@PostMapping("/edit")
	@ResponseStatus(HttpStatus.OK)
	public String editStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
		inventoryService.editStock(skuCode, quantity);
		return "edit ss";
	}
	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.OK)
	public String insertStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
		inventoryService.insertStock(skuCode, quantity);
		return "insert ss";
	}
}