package ru.geekbrains.spring.market.core.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.services.OrderService;

import java.net.http.HttpResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void createOrderTest() throws Exception {
        // Как протестировать, что пользователь не авторизован?

//        Mockito.when(orderService.createOrder("junior")).thenReturn(
//                HttpStatus.UNAUTHORIZED);
//
//        mvc.perform(
//                post("/api/v1/orders")
//                        .header("username", "junior"))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());

        mvc.perform(
                        post("/api/v1/orders")
                                .header("username", "Bob")
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
