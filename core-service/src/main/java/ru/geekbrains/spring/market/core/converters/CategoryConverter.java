package ru.geekbrains.spring.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.api.CategoryDto;
import ru.geekbrains.spring.market.core.entities.Category;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter {

    private final ProductConverter productConverter;

    public CategoryDto entityToDto(Category category) {
        CategoryDto c = new CategoryDto();
        c.setId(category.getId());
        c.setTitle(category.getTitle());
        c.setProducts(category.getProducts().stream()
                .map(productConverter::entityToDto).collect(Collectors.toList()));

        return c;
    }
}
