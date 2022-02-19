package ru.geekbrains.spring.market.dtos;

import lombok.Data;
import ru.geekbrains.spring.market.entities.Product;
import ru.geekbrains.spring.market.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        CartItem cartItem = getItem(product.getId());
        if (cartItem == null) {
            items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setPrice(cartItem.getPricePerProduct() * cartItem.getQuantity());
        }
        recalculate();
    }

    public void remove(Long productId) {
        CartItem cartItem = getItem(productId);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItem.setPrice(cartItem.getPricePerProduct() * cartItem.getQuantity());
        recalculate();
    }

    public void exclude(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculate();
    }

    private CartItem getItem(Long productId) {
        return items.stream().filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }
}
