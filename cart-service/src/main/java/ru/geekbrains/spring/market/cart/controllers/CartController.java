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
    public void addToCart(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.remove(id);
    }

    @GetMapping("/exclude/{id}")
    public void excludeItemFromCart(@PathVariable Long id) {
        cartService.exclude(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }
}
