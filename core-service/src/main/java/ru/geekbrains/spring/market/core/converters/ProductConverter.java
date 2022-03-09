package ru.geekbrains.spring.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.entities.Category;
import ru.geekbrains.spring.market.core.entities.Product;
import ru.geekbrains.spring.market.core.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final CategoryService categoryService;

    public ProductDto entityToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory().getTitle()
        );
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        Category c = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        p.setCategory(c);

        return p;
    }
}
