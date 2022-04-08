package ru.geekbrains.spring.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.api.AppError;
import ru.geekbrains.spring.api.PageDto;
import ru.geekbrains.spring.api.ProductDto;
import ru.geekbrains.spring.api.ResourceNotFoundException;
import ru.geekbrains.spring.market.core.converters.ProductConverter;
import ru.geekbrains.spring.market.core.entities.Product;
import ru.geekbrains.spring.market.core.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )
    @GetMapping
    public PageDto<ProductDto> findProducts(
            @Parameter(description = "Название продукта") @RequestParam(name = "title_part", required = false) String titlePart,
            @Parameter(description = "Минимальная цена") @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @Parameter(description = "Максимальная цена") @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            @Parameter(description = "Номер страницы списка") @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> jpaPage = productService.findAll(titlePart, minPrice, maxPrice, page - 1)
                .map(productConverter::entityToDto);

        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setItems(jpaPage.getContent());
        out.setTotalPages(jpaPage.getTotalPages());

        return out;
    }

    @Operation(
            summary = "Поиск продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findProductById(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable Long id) {
        Product p = productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Продукт не найден, id: " + id));

        return productConverter.entityToDto(p);
    }

    @Operation(
            summary = "Создает новый продукт",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(
            @Parameter(description = "Новый продукт", required = true) @RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }

    @Operation(
            summary = "Удаляет продукт",
            responses = {
                    @ApiResponse(
                            description = "Продукт удален", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(
            @Parameter(description = "Идентификатор продукта", required = true) @PathVariable Long id) {
        productService.deleteById(id);
    }

}
