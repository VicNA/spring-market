package ru.geekbrains.spring.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель элемента заказа")
public class OrderItemDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    @Schema(description = "Название продукта", required = true, example = "Milk")
    private String productTitle;

    @Schema(description = "ID заказа", required = true, example = "1")
    private Long orderId;

    @Schema(description = "Количество продуктов в позиции заказа", required = true, example = "2")
    private int quantity;

    @Schema(description = "Цена единичного продукта", required = true, example = "89.99")
    private BigDecimal pricePerProduct;

    @Schema(description = "Цена продуктов в позиции заказа", required = true, example = "189.5")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
