package ru.geekbrains.spring.market.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@Slf4j
public class Cart {

    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
        log.info("Инициализация корзины");
    }

    public void add(Product product) {
        products.add(product);
        log.info("Добавление в корзину (List)");
    }
}
