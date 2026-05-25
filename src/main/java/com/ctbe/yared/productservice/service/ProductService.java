package com.ctbe.yared.productservice.service;

import com.ctbe.yared.productservice.dto.CreateProductRequest;
import com.ctbe.yared.productservice.dto.ProductDTO;
import com.ctbe.yared.productservice.exception.ResourceNotFoundException;
import com.ctbe.yared.productservice.mapper.ProductMapper;
import com.ctbe.yared.productservice.model.Category;
import com.ctbe.yared.productservice.model.Product;
import com.ctbe.yared.productservice.repository.CategoryRepository;
import com.ctbe.yared.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper mapper;

    // ── Create ──────────────────────────────────────────────────────────
    public ProductDTO create(CreateProductRequest req) {
        Category cat = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found: " + req.categoryId()));

        Product p = Product.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .stock(req.stock())
                .slug(slugify(req.name()))
                .category(cat)
                .build();

        return mapper.toDTO(productRepo.save(p));
    }

    // ── Paginated list (the JOIN FETCH query) ────────────────────────────
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepo.findAllWithCategory(pageable).map(mapper::toDTO);
    }

    // ── Find by ID ───────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productRepo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    // ── Find by slug ─────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductDTO findBySlug(String slug) {
        return productRepo.findBySlug(slug)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with slug: " + slug));
    }

    // ── Search ───────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductDTO> search(String keyword, BigDecimal maxPrice) {
        List<Product> results = (keyword != null && !keyword.isBlank())
                ? productRepo.searchByKeyword(keyword)
                : productRepo.findAll();

        return results.stream()
                .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
                .map(mapper::toDTO)
                .toList();
    }

    // ── Soft delete (updates deleted=true via @SQLDelete) ────────────────
    public void delete(Long id) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        productRepo.delete(p); // fires: UPDATE products SET deleted=TRUE WHERE id=?
        log.info("Soft-deleted product id={} name={}", p.getId(), p.getName());
    }

    // ── Slug helper ──────────────────────────────────────────────────────
    private String slugify(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
