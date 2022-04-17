package ru.geekbrains.spring.market.core.controllers;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.api.StringResponse;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @RequestMapping
    public StringResponse helloAdmin(
            @RequestHeader(name = "role") String role) {
        if (!role.equals("ADMIN")) {
            throw new ResourceNotFoundException("Доступ запрещен");
        }
        return new StringResponse("admin");
    }
}
