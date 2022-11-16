package com.veiter.animalcare.security.response;

import com.veiter.animalcare.models.AnimalInfo;
import org.springframework.data.domain.Page;

public class CategoryPage {
    private String category;
    private Page<AnimalInfo> page;

    public CategoryPage(String category, Page<AnimalInfo> page) {
        this.category = category;
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Page<AnimalInfo> getPage() {
        return page;
    }

    public void setPage(Page<AnimalInfo> page) {
        this.page = page;
    }
}
