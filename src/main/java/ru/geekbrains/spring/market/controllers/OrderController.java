package ru.geekbrains.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.market.entities.User;
import ru.geekbrains.spring.market.services.OrderService;
import ru.geekbrains.spring.market.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    public void createOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(()->new RuntimeException()); // TODO: 24.02.2022 Создать свое исключение
        orderService.createOrder(user);
    }
}
