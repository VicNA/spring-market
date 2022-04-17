package ru.geekbrains.spring.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Страница с элементами")
public class PageDto<E> {
    @Schema(description = "Список элементов", required = true)
    private List<E> items;

    @Schema(description = "Номер страницы", required = true)
    private int page;

    @Schema(description = "Общее количество страниц", required = true)
    private int totalPages;

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PageDto() {
    }
}
