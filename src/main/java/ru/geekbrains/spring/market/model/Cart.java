package ru.geekbrains.spring.market.model;

import lombok.Data;
import ru.geekbrains.spring.market.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void add(Product product) {
        CartItem cartItem = findItem(product.getId());
        if (cartItem == null) {
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        } else {
            cartItem.changeQuantity(1);
        }
        recalculate();
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
        totalPrice = 0;
    }

    private CartItem findItem(Long productId) {
        return items.stream().filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
    }
}
