package ru.geekbrains.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.market.services.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
}
