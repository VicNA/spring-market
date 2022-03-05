package ru.geekbrains.spring.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.carts.model.Cart;
import ru.geekbrains.spring.market.carts.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
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
