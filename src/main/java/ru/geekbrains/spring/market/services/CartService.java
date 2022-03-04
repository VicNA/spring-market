package ru.geekbrains.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.model.Cart;
import ru.geekbrains.spring.market.entities.Product;
import ru.geekbrains.spring.market.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private Cart tempCart;

    public Cart getCurrentCart() {
        return tempCart;
    }

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public void add(Long productId) {
        Product product = productService.findById(productId).orElseThrow(
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
