package com.example.demo.routes;

import java.net.URI;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
@Configuration(proxyBeanMethods = false)
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> TestServiceRoute() {
        return route("test-service")
                .route(RequestPredicates.path("/api/all"), http("http://test-service"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> testServiceRoute(DiscoveryClient discoveryClient) {
        return route()
                .GET("/api/test/all", request -> {
                    String serviceUrl = discoveryClient.getInstances("auth-service")
                        .stream()
                        .findFirst()
                        .map(instance -> instance.getUri().toString())
                        .orElseThrow(() -> new RuntimeException("Service instance not found"));

                    return ServerResponse.temporaryRedirect(URI.create(serviceUrl + "/api/test/all")).build();
                })
                .build();
    }
}
