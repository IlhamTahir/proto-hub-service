package com.bilitech.api.core.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResult<T> {
    private Integer size;
    private Long total;
    private Integer page;
    private List<T> data;

    public PageResult(Page<T> page) {
        this.data = page.getContent();
        this.total = page.getTotalElements();
        this.page =  page.getNumber() + 1;
        this.size = page.getSize();
    }
}