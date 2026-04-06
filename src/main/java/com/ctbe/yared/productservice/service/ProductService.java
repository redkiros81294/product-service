package com.ctbe.yared.productservice.service;

import com.ctbe.yared.productservice.dto.ProductRequest;
import com.ctbe.yared.productservice.dto.ProductResponse;
import com.ctbe.yared.productservice.exception.ResourceNotFoundException;
import com.ctbe.yared.productservice.model.Product;
import com.ctbe.yared.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<ProductResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse findById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toResponse(product);
    }

    public ProductResponse create(ProductRequest req) {
        Product product = toEntity(req);
        return toResponse(repo.save(product));
    }

    public ProductResponse update(Long id, ProductRequest req) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        existing.setName(req.getName());
        existing.setPrice(req.getPrice());
        existing.setStockQty(req.getStockQty());
        existing.setCategory(req.getCategory());
        return toResponse(repo.save(existing));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        repo.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getStockQty(), p.getCategory());
    }

    private Product toEntity(ProductRequest req) {
        return new Product(req.getName(), req.getPrice(), req.getStockQty(), req.getCategory());
    }
}
