package ru.geekbrains.spring.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.spring.market.cart.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> userCart;

    @PostConstruct
    public void init() {
        userCart = new HashMap<String, Cart>();
    }

    private Cart getCart(String username) {
        if (!userCart.containsKey(username)) {
            userCart.put(username, new Cart());
        }
        return userCart.get(username);
    }

    public Cart getCurrentCart(String username) {
        return getCart(username);
    }

    public void add(String username, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        getCart(username).add(product);
    }

    public void remove(String username, Long productId) {
        userCart.get(username).remove(productId);
    }

    public void exclude(String username, Long productId) {
        userCart.get(username).exclude(productId);
    }

    public void clear(String username) {
        userCart.remove(username);
    }
}
