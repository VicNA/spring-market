package ru.geekbrains.spring.market.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.spring.market.cart.model.Cart;
import ru.geekbrains.spring.market.cart.services.CartService;

import java.math.BigDecimal;

@SpringBootTest(classes = CartService.class)
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @MockBean
    private Cart tempCart;

//    @BeforeEach
//    public void init() {
//        tempCart = new Cart();
//    }

    @Test
    public void addTest() {
        ProductDto productDto = new ProductDto(100L, "Juice", BigDecimal.valueOf(120), "Other");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(100L);

//        Cart cart = Mockito.mock(Cart.class);
        Mockito.doNothing().when(tempCart).add(productDto);
        cartService.add(100L);

        Mockito.verify(tempCart, Mockito.times(1)).add(productDto);
        // Почему Wanted but not invoked ?
        // И вообще, как тестировать void методы
    }
}
