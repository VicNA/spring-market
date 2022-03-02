package ru.geekbrains.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.entities.ProductEntity;
import ru.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.market.repositories.ProductRepository;
import ru.geekbrains.spring.market.soap.products.Product;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private static final Function<ProductEntity, Product> functionEntiryToSoap = pe -> {
        Product product = new Product();
        product.setId(pe.getId());
        product.setTitle(pe.getTitle());
        product.setPrice(pe.getPrice());
        return product;
    };

    public Product getById(Long id) {
        return productRepository.findById(id).map(functionEntiryToSoap).orElseThrow(
                () -> new ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().map(functionEntiryToSoap).collect(Collectors.toList());
    }
}
