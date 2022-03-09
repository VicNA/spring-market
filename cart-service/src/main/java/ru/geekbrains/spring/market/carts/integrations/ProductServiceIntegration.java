package ru.geekbrains.spring.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.spring.api.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    @Value("${module.products.url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject(productUrl + id, ProductDto.class));
    }
}
