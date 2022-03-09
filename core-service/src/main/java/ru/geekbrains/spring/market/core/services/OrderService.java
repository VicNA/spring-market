package ru.geekbrains.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.api.CartDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.entities.Order;
import ru.geekbrains.spring.market.core.entities.OrderItem;
import ru.geekbrains.spring.market.core.entities.User;
import ru.geekbrains.spring.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.spring.market.core.repositories.OrderItemRepository;
import ru.geekbrains.spring.market.core.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(User user) {
        CartDto cart = cartServiceIntegration.getCurrentCart().orElseThrow(
                () -> new ResourceNotFoundException("Не удается найти текущую корзину"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());
        orderRepository.save(order);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(productService.findById(cartItem.getProductId()).orElseThrow(
                            () -> new ResourceNotFoundException("Не удается добавить продукт с id: " + cartItem.getProductId()
                                    + " в заказ. Продукт не найден")
                    ));
                    orderItem.setOrder(order);
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setPricePerProduct(cartItem.getPricePerProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItemRepository.save(orderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }
}
