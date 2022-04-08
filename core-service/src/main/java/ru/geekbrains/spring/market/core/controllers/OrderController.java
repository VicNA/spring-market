package ru.geekbrains.spring.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.api.OrderDto;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.core.converters.OrderConverter;
import ru.geekbrains.spring.market.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Формирует новый заказ",
            responses = {
                    @ApiResponse(
                            description = "Заказ успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(
            @Parameter(description = "Имя пользователя", required = true) @RequestHeader String username) {
        orderService.createOrder(username);
    }

    @Operation(
            summary = "Запрос списка заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(
            @Parameter(description = "Имя пользователя", required = true) @RequestHeader String username) {
        return orderService.findByUsername(username).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
