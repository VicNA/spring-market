package ru.geekbrains.spring.market.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.api.CartDto;
import ru.geekbrains.spring.api.StringResponse;
import ru.geekbrains.spring.market.cart.convertes.CartConverter;
import ru.geekbrains.spring.market.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addToCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid,
                          @PathVariable Long id) {
        cartService.add(username, uuid, id);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username,
                                  @PathVariable String uuid) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username, uuid));
    }

    @GetMapping("/{uuid}/remove/{id}")
    public void removeFromCart(@RequestHeader(name = "username", required = false) String username,
                               @PathVariable String uuid,
                               @PathVariable Long id) {
        cartService.remove(username, uuid, id);
    }

    @GetMapping("/{uuid}/exclude/{id}")
    public void excludeItemFromCart(@RequestHeader(name = "username", required = false) String username,
                                    @PathVariable String uuid,
                                    @PathVariable Long id) {
        cartService.exclude(username, uuid, id);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username,
                          @PathVariable String uuid) {
        cartService.clear(username, uuid);
    }
}
