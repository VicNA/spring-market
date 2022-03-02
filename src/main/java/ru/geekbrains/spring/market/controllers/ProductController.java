package ru.geekbrains.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.dtos.ProductDto;
import ru.geekbrains.spring.market.entities.ProductEntity;
import ru.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.market.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAll().stream()
                .map(p -> new ProductDto(p.getId(), p.getTitle(), p.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        ProductEntity p = productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Продукт не найден, id: " + id));

        return new ProductDto(p.getId(), p.getTitle(), p.getPrice());
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
