package ru.geekbrains.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.entities.Category;
import ru.geekbrains.spring.market.core.entities.Product;
import ru.geekbrains.spring.market.core.repositories.ProductRepository;
import ru.geekbrains.spring.market.core.repositories.specifications.ProductsSpecifications;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(String partTitle, BigDecimal minPrice, BigDecimal maxPrice, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }

        return productRepository.findAll(spec, PageRequest.of(page, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        productRepository.save(product);

        return product;
    }
}
