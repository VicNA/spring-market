package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.market.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
