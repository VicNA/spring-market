package ru.geekbrains.spring.market.cart.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.spring.market.cart.model.Cart;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @Spy
    private Cart cart;

    @BeforeEach
    public void initCart() {
        cart = cartService.getCurrentCart("bob");

        ProductDto productDto = new ProductDto(100L, "Juice", BigDecimal.valueOf(120), "Other");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(100L);

        productDto = new ProductDto(200L, "Juice2", BigDecimal.valueOf(120), "Other");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(200L);
    }

//    @Test
//    public void addTest() {
//        cartService.add(100L);
//        cartService.add(100L);
//        cartService.add(200L);
//
//        assertEquals(2, cart.getItems().size());
//        assertEquals(BigDecimal.valueOf(360), cart.getTotalPrice());
//    }
//
//    @Test
//    public void removeTest() {
//        cartService.remove(100L);
//
//        assertEquals(2, cart.getItems().size());
//        assertEquals(BigDecimal.valueOf(240), cart.getTotalPrice());
//    }
//
//    @Test
//    public void excludeTest() {
//        cartService.exclude(100L);
//
//        assertEquals(1, cart.getItems().size());
//        assertEquals(BigDecimal.valueOf(120), cart.getTotalPrice());
//    }
//
//    @Test
//    public void clearTest() {
//        cartService.clear();
//
//        assertEquals(0, cart.getItems().size());
//    }
}
