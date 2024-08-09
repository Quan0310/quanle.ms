package com.example.demo.util;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public class ElasticsearchUtil {

	 private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchUtil.class);
	 
   public static Supplier<Query> supplier(){
       Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
       return supplier;
   }

   public static MatchAllQuery matchAllQuery(){
   	 MatchAllQuery.Builder matchAllQueryBuilder = new MatchAllQuery.Builder();
   	    return matchAllQueryBuilder.build();
   }

   public static Supplier<Query> supplierWithNameField(String fieldValue){
       Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithNameField(fieldValue)));
       return supplier;
   }

   public static MatchQuery matchQueryWithNameField(String fieldValue){
   	MatchQuery.Builder matchQueryBuilder = new MatchQuery.Builder();
       return matchQueryBuilder.field("name").query(fieldValue).build();
   }
       
   
   public static Supplier<Query> supplierWithNameField2nd(String fieldValue) {
       Supplier<Query> supplier = () -> Query.of(q -> q.prefix(prefixQueryWithNameField(fieldValue)));
       return supplier;
   }

   public static PrefixQuery prefixQueryWithNameField(String fieldValue) {
       PrefixQuery.Builder prefixQueryBuilder = new PrefixQuery.Builder();
       return prefixQueryBuilder.field("name").value(fieldValue).build();
   }
   
   public static Supplier<Query> supplierWithNameField3rd(String fieldValue) {
   	LOG.info("FuzzyQuery iss:"+ fuzzyQueryWithNameField(fieldValue));
   	 Supplier<Query> supplier = () -> Query.of(q -> q.fuzzy(fuzzyQueryWithNameField(fieldValue)));
        return supplier;
   }
   
   public static FuzzyQuery fuzzyQueryWithNameField(String fieldValue) {
   	FuzzyQuery.Builder fuzzyQueryBuilder = new FuzzyQuery.Builder();
   	return fuzzyQueryBuilder.field("name").value(fieldValue).build();
   }
   
}