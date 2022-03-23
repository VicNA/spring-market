package ru.geekbrains.spring.market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.market.core.entities.Category;
import ru.geekbrains.spring.market.core.entities.Product;
import ru.geekbrains.spring.market.core.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @SpyBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void initProduct() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Food");
        Mockito.doReturn(Optional.of(category)).when(categoryService).findByTitle("Food");

        Product product = new Product();
        product.setId(9000L);
        product.setTitle("Potato");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(category);
        Mockito.doReturn(Optional.of(product)).when(productRepository).findById(9000L);
    }

    // Как можно протестировать метод у которого есть спецификация?

    @Test
    public void createNewProductTest() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Food");
        Mockito.doReturn(Optional.of(category)).when(categoryService).findByTitle("Food");

        ProductDto productDto = new ProductDto();
        productDto.setId(9000L);
        productDto.setTitle("Potato");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setCategoryTitle("Food");

        Product product = productService.createNewProduct(productDto);
        assertEquals(product.getPrice(), BigDecimal.valueOf(10));
        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
