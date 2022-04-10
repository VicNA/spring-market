package ru.geekbrains.spring.market.cart.model;

import lombok.Data;
import ru.geekbrains.spring.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(ProductDto product) {
        CartItem cartItem = findItem(product.getId());
        if (cartItem == null) {
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        } else {
            cartItem.changeQuantity(1);
        }
        recalculate();
    }

    public void changeItemQuantity(Long productId) {
        findItem(productId).changeQuantity(1);
    }

    public void remove(Long productId) {
        CartItem cartItem = findItem(productId);
        cartItem.changeQuantity(-1);
        recalculate();
    }

    public void exclude(Long productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private CartItem findItem(Long productId) {
        return items.stream().filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public boolean isPresent(Long productId) {
        return findItem(productId) != null;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }
}
