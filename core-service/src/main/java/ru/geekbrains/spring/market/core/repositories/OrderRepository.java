package ru.geekbrains.spring.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.market.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
