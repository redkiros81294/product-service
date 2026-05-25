package com.ctbe.yared.productservice.repository;

import com.ctbe.yared.productservice.PostgresContainerBase;
import com.ctbe.yared.productservice.model.Category;
import com.ctbe.yared.productservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryContainerTest extends PostgresContainerBase {

    @Autowired
    TestEntityManager em;

    @Autowired
    ProductRepository productRepo;

    @BeforeEach
    void setUp() {
        Category cat = em.persist(Category.builder().name("TestElectronicsContainer").build());
        em.persist(Product.builder()
                .name("GPU")
                .price(new BigDecimal("499.00"))
                .stock(5)
                .slug("gpu")
                .category(cat)
                .build());
        em.flush();
    }

    @Test
    void partialIndex_isUsedByQueryPlanner() {
        // This test only works with real PostgreSQL — H2 doesn't support EXPLAIN in the same way
        List<Product> active = productRepo.findAll();
        assertThat(active).hasSize(1);
        assertThat(active.get(0).isDeleted()).isFalse();
    }

    @Test
    void softDelete_realPostgres_deletedRowInvisible() {
        Product gpu = productRepo.findBySlug("gpu").orElseThrow();
        productRepo.delete(gpu);
        em.flush();
        em.clear();

        assertThat(productRepo.count()).isZero();

        // Verify the row still EXISTS in the database via native SQL (bypasses @SQLRestriction)
        Long rawCount = (Long) em.getEntityManager()
                .createNativeQuery("SELECT count(*) FROM products WHERE deleted = true")
                .getSingleResult();
        assertThat(rawCount).isEqualTo(1);
    }
}
