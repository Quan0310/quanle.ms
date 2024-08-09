package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.demo.model.Product;
import com.example.demo.service.ElasticsearchService;
import com.example.demo.service.ProductService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Autowired
	ElasticsearchService elasticsearchService;
	@Autowired
	ProductService productService;
	
	@PostConstruct
    public void afterStartup() throws IOException {
        System.out.println("Bắt đầu đồng bộ...");
        elasticsearchService.deleteAllProductsFromElasticsearch();
        Iterable<Product> products = productService.getProductsFromMySQLToSync();
        elasticsearchService.addProductsBulk(products);
    }
	
}
