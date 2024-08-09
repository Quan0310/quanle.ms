package com.example.demo.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.elastic.ProductElasticsearchRepository;
import com.example.demo.repository.jpa.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

   @Autowired
   ProductRepository productRepository;
   
   @Autowired
   ProductElasticsearchRepository elasticsearchRepository;

    public void createProduct(ProductRequest productRequest) {
    	  Product product = new Product();
          product.setName(productRequest.name());
          product.setPrice(productRequest.price());
          product.setDescription(productRequest.description());
          product.setSkuCode(UUID.randomUUID().toString());
          productRepository.save(product);
          elasticsearchRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.get();
    }
	public void updateProduct(ProductRequest productRequest, Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		Product product = productOptional.get();
		if (product != null) {
			product.setName(productRequest.name());
			product.setDescription(productRequest.description());
			product.setPrice(productRequest.price());
			Product updatedProduct = productRepository.save(product);
			elasticsearchRepository.save(updatedProduct);
		}
	}
	public void deleteProduct(Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			elasticsearchRepository.deleteById(id);
		}
	}
    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }
	public Iterable<Product> getProductsFromMySQLToSync() {
		return productRepository.findAll();
	}
}