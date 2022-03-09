package ru.geekbrains.spring.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.api.CartDto;
import ru.geekbrains.spring.market.carts.convertes.CartConverter;
import ru.geekbrains.spring.market.carts.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
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
