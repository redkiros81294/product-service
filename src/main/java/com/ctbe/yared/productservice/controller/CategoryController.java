package com.ctbe.yared.productservice.controller;

import com.ctbe.yared.productservice.dto.CategoryRequest;
import com.ctbe.yared.productservice.model.Category;
import com.ctbe.yared.productservice.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody CategoryRequest req) {
        Category cat = Category.builder()
                .name(req.name())
                .description(req.description())
                .build();
        return categoryRepo.save(cat);
    }

    @GetMapping
    public java.util.List<Category> list() {
        return categoryRepo.findAll();
    }
}
