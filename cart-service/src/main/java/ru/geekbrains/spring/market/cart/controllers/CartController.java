package ru.geekbrains.spring.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.api.CartDto;
import ru.geekbrains.spring.market.cart.convertes.CartConverter;
import ru.geekbrains.spring.market.cart.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@RequestHeader(name = "username") String username, @PathVariable Long id) {
        cartService.add(username, id);
    }

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(name = "username") String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username));
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@RequestHeader(name = "username") String username, @PathVariable Long id) {
        cartService.remove(username, id);
    }

    @GetMapping("/exclude/{id}")
    public void excludeItemFromCart(@RequestHeader(name = "username") String username, @PathVariable Long id) {
        cartService.exclude(username, id);
    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader(name = "username") String username) {
        cartService.clear(username);
    }
}
