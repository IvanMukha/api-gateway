package com.mukha.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GatewayRoutesConfig.GatewayServicesProperties.class)
@RequiredArgsConstructor
public class GatewayRoutesConfig {

    private final GatewayServicesProperties properties;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-route", r -> r
                        .path("/v1/api/users/**", "/v1/api/cards/**")
                        .uri(properties.userServiceUrl()))

                .route("auth-service-route", r -> r
                        .path("/v1/api/auth/**")
                        .uri(properties.authServiceUrl()))

                .route("order-service-route", r -> r
                        .path("/v1/api/items/**", "/v1/api/orders/**")
                        .uri(properties.orderServiceUrl()))

                .route("payment-service-route", r-> r
                        .path("/v1/api/payments/**")
                        .uri(properties.paymentServiceUrl))
                .build();

    }

    @ConfigurationProperties
    public record GatewayServicesProperties(
            String userServiceUrl,
            String authServiceUrl,
            String orderServiceUrl,
            String paymentServiceUrl
    ) {}
}