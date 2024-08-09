package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.AuthClient;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.service.ElasticsearchService;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    AuthClient authClient;
    
    @Autowired
    ElasticsearchService elasticsearchService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }
    @GetMapping("/test")
    String test() {
    	return authClient.getUserDetails("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5hbmEyIiwiaWF0IjoxNzIzMTA2ODQ2LCJleHAiOjE3MjMxNDI4NDZ9.BABwcmeXmq7Y_hzvqWbsBz1VT54XSY5s-XetMXHRY4yYjAXc3c7k6mqZ22bkTobpFEBDazalKPrUJ9IcyO2lLw");
    }
    
    @GetMapping("/matchAllProducts")
    public List<Product> matchAllProductsWithName(@RequestParam String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  elasticsearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
    
    @PostMapping("/edit/{productId}")
    public String editProduct(@RequestBody ProductRequest product, @PathVariable Long productId){
       productService.updateProduct(product, productId);
       return "aaa";
    }
    
    @PostMapping("/delete/{productId}")
    public String deleteProduct( @PathVariable Long productId){
        productService.deleteProduct(productId);
        return "bbb";
    }
}