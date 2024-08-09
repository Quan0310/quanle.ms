package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.util.ElasticsearchUtil;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;

@Service
public class ElasticsearchService {

	@Autowired
	private ProductService productService;
	@Autowired
	private ElasticsearchClient elasticsearchClient;

	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchService.class);

	public SearchResponse<Map> matchAllServices() throws IOException {
		Supplier<Query> supplier = ElasticsearchUtil.supplier();
		SearchResponse<Map> searchResponse = elasticsearchClient.search(s -> s.query(supplier.get()), Map.class);
		System.out.println("elasticsearch query is " + supplier.get().toString());
		return searchResponse;
	}

	public SearchResponse<Product> matchAllProductsServices() throws IOException {
		Supplier<Query> supplier = ElasticsearchUtil.supplier();
		SearchResponse<Product> searchResponse = elasticsearchClient
				.search(s -> s.index("products").query(supplier.get()), Product.class);
		System.out.println("elasticsearch query is " + supplier.get().toString());
		return searchResponse;
	}


	public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
		Supplier<Query> supplier = ElasticsearchUtil.supplierWithNameField3rd(fieldValue);
		LOG.info("elasticsearchs query is " + supplier.get().toString());
		SearchResponse<Product> searchResponse = elasticsearchClient
				.search(s -> s.index("products").query(supplier.get()), Product.class);
		return searchResponse;
	}

	public void deleteAllProductsFromElasticsearch() {
		try {
			DeleteByQueryRequest deleteByQueryRequest = DeleteByQueryRequest
					.of(b -> b.index("products").query(q -> q.matchAll(m -> m)));

			DeleteByQueryResponse response = elasticsearchClient.deleteByQuery(deleteByQueryRequest);
		
			System.out.println("deleteByQueryRequest: " + deleteByQueryRequest);
			System.out.println("Deleted documents: " + response.deleted());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public void addProductsBulk(Iterable<Product> products) throws IOException {
	        BulkRequest.Builder bulkRequest = new BulkRequest.Builder();

	        for (Product product : products) {
	        	bulkRequest.operations(op -> op           
	                    .index(idx -> idx            
	                        .index("products")       
	                        .id(product.getId().toString())
	                        .document(product)
	                    )
	                );
	        }
	        BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest.build());

	        if (bulkResponse.errors()) {
	            LOG.info("Bulk had errors");
	            for (BulkResponseItem item: bulkResponse.items()) {
	                if (item.error() != null) {
	                    LOG.error(item.error().reason());
	                }
	            }
	        }
	    }

}
