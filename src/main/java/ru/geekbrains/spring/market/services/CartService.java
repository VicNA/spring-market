package ru.geekbrains.spring.market.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.entities.Product;
import ru.geekbrains.spring.market.utils.Cart;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductService productService;
    private final Cart cart;

    public Cart getCart() {
        log.info("Возвращение корзины (CartService): {}", cart.getProducts());
        return cart;
    }

    public void add(Long id) {
        Product product = productService.findById(id).get();
        cart.add(product);
        log.info("Добавить в корзину (CartService)");
    }
}
