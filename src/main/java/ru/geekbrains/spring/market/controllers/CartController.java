package ru.geekbrains.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.services.CartService;
import ru.geekbrains.spring.market.model.Cart;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @DeleteMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
    }

    @DeleteMapping("/exclude/{id}")
    public void excludeItemFromCart(@PathVariable Long id) {
        cartService.exclude(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}
