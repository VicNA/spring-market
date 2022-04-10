package ru.geekbrains.spring.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.spring.market.cart.model.Cart;
import ru.geekbrains.spring.market.cart.model.CartItem;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String username, String uuid) {
        // Реализация объединения ТОЛЬКО позиции элементов корзины
        if (username != null) {
            Cart uuidCard = getCurrentCart(cartPrefix + uuid);
            if (uuidCard.getItems().size() > 0) {
                Cart userCard = getCurrentCart(cartPrefix + username);
                uuidCard.getItems().removeAll(userCard.getItems());
                userCard.getItems().addAll(uuidCard.getItems());
                uuidCard.clear();
                redisTemplate.opsForValue().set(cartPrefix + username, userCard);
                redisTemplate.opsForValue().set(cartPrefix + uuid, uuidCard);
            }
        }
        return getCurrentCart(getCartUuid(username, uuid));
    }

    public Cart getCurrentCart(String targetUuid) {
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void add(String username, String uuid, Long productId) {
        String targetUuid = getCartUuid(username, uuid);
        Cart currentCart = getCurrentCart(targetUuid);
        if (!currentCart.isPresent(productId)) {
            ProductDto product = productServiceIntegration.getProductById(productId);
            execute(targetUuid, cart -> cart.add(product));
        } else {
            execute(targetUuid, cart -> cart.changeItemQuantity(productId));
        }
    }

    public void remove(String username, String uuid, Long productId) {
        execute(getCartUuid(username, uuid), cart -> cart.remove(productId));
    }

    public void exclude(String username, String uuid, Long productId) {
        execute(getCartUuid(username, uuid), cart -> cart.exclude(productId));
    }

    public void clear(String username, String uuid) {
        execute(getCartUuid(username, uuid), Cart::clear);
    }

    private void execute(String targetUuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(targetUuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(targetUuid, cart);
    }

    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return cartPrefix + username;
        }
        return cartPrefix + uuid;
    }
}
