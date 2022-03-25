package ru.geekbrains.spring.market.cart.controllers;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.cart.integrations.ProductServiceIntegration;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @BeforeEach
    public void initCart() {
        ProductDto productDto = new ProductDto(100L, "Juice", BigDecimal.valueOf(120), "Other");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(100L);

        productDto = new ProductDto(200L, "Juice2", BigDecimal.valueOf(120), "Other");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(200L);
    }

    @Test
    @Order(1)
    public void addToCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/add/100"))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart/add/100"))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart/add/200"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void getCurrentCartTest() throws Exception {
        mvc.perform(
                get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].quantity", is(2)));
    }

    @Test
    @Order(3)
    public void removeFromCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/remove/100"))
                .andExpect(status().isOk());

        mvc.perform(
                        get("/api/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)));
    }

    @Test
    @Order(4)
    public void excludeItemFromCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/exclude/100"))
                .andExpect(status().isOk());

        mvc.perform(
                        get("/api/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)));
    }

    @Test
    @Order(5)
    public void clearCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/clear"))
                .andExpect(status().isOk());

        mvc.perform(
                        get("/api/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(0)));
    }
}
