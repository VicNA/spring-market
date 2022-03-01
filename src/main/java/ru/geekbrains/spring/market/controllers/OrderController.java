package ru.geekbrains.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.market.entities.User;
import ru.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.market.services.OrderService;
import ru.geekbrains.spring.market.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/create")
    public void createOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("Не удается найти пользователя c именем: " + principal.getName()
                        + ". Заказ не может быть оформлен"));
        orderService.createOrder(user);
    }
}
