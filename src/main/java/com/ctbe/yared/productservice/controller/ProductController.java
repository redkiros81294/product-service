package com.ctbe.yared.productservice.controller;

import com.ctbe.yared.productservice.dto.CreateProductRequest;
import com.ctbe.yared.productservice.dto.ProductDTO;
import com.ctbe.yared.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product catalogue endpoints")
public class ProductController {

    private final ProductService productService;

    // GET /api/v1/products?page=0&size=10&sort=price,asc
    @GetMapping
    @Operation(summary = "List all products (paginated)")
    public ResponseEntity<Page<ProductDTO>> list(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    // GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    // GET /api/v1/products/search?keyword=java&maxPrice=50.00
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.search(keyword, maxPrice));
    }

    // GET /api/v1/products/slug/{slug}
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.findBySlug(slug));
    }

    // POST /api/v1/products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody CreateProductRequest req) {
        return productService.create(req);
    }

    // DELETE /api/v1/products/{id} — soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
