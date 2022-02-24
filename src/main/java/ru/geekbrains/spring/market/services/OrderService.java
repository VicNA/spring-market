package ru.geekbrains.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.entities.User;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;

    public void createOrder(User user) {

    }
}
