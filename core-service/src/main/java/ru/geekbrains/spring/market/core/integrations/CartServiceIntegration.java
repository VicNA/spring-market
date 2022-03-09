package ru.geekbrains.spring.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.spring.api.CartDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    @Value("${module.carts.url}")
    private String cartUrl;

    private final RestTemplate restTemplate;

    public Optional<CartDto> getCurrentCart() {
        return Optional.ofNullable(restTemplate.getForObject(cartUrl, CartDto.class));
    }
}
