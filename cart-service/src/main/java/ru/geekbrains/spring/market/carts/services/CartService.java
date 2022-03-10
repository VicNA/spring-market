package ru.geekbrains.spring.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.spring.market.carts.model.Cart;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Не удается добавить продукт с id: " + productId + " в корзину. Продукт не найден"));
        tempCart.add(product);
    }

    public void remove(Long productId) {
        tempCart.remove(productId);
    }

    public void exclude(Long productId) {
        tempCart.exclude(productId);
    }

    public void clear() {
        tempCart.clear();
    }
}