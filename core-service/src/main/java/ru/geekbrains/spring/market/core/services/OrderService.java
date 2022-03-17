package ru.geekbrains.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.api.CartDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.entities.Order;
import ru.geekbrains.spring.market.core.entities.OrderItem;
import ru.geekbrains.spring.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.spring.market.core.repositories.OrderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart();

        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream()
                .map(cartItem -> new OrderItem(
                                productService.findById(cartItem.getProductId()).orElseThrow(
                                        () -> new ResourceNotFoundException("Не удается добавить продукт с id: "
                                                + cartItem.getProductId() + " в заказ. Продукт не найден")),
                                order,
                                cartItem.getQuantity(),
                                cartItem.getPrice(),
                                cartItem.getPricePerProduct()
                        )
                ).collect(Collectors.toList()));
        orderRepository.save(order);

        cartServiceIntegration.clear();
    }
}
