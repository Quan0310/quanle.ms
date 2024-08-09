package com.example.demo.repository.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.model.Product;

public interface ProductElasticsearchRepository extends ElasticsearchRepository<Product, Long> {

}
