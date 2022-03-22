package ru.geekbrains.spring.market.cart.convertes;

import org.springframework.stereotype.Component;
import ru.geekbrains.spring.api.CartItemDto;
import ru.geekbrains.spring.market.cart.model.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto entityToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItem.getProductId());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());

        return cartItemDto;
    }
}
