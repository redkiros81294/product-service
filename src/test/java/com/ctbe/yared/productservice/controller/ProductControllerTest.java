package com.ctbe.yared.productservice.controller;

import com.ctbe.yared.productservice.dto.CreateProductRequest;
import com.ctbe.yared.productservice.dto.ProductDTO;
import com.ctbe.yared.productservice.exception.ResourceNotFoundException;
import com.ctbe.yared.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ActiveProfiles("dev")
class ProductControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void getProducts_returnsPaginatedResponse() throws Exception {
        ProductDTO dto = new ProductDTO(1L, "Laptop", null,
                new BigDecimal("999.00"), 10, "laptop", "Electronics", 1L, null);
        Page<ProductDTO> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);

        when(productService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/products").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Laptop"))
                .andExpect(jsonPath("$.content[0].categoryName").value("Electronics"));
    }

    @Test
    void getProductById_whenNotFound_returns404() throws Exception {
        when(productService.findById(999L))
                .thenThrow(new ResourceNotFoundException("Product not found: 999"));

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Product not found: 999"));
    }

    @Test
    void createProduct_withValidBody_returns201() throws Exception {
        CreateProductRequest req = new CreateProductRequest(
                "Keyboard", null, new BigDecimal("49.99"), 20, 1L);
        ProductDTO dto = new ProductDTO(2L, "Keyboard", null,
                new BigDecimal("49.99"), 20, "keyboard", "Electronics", 1L, null);

        when(productService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.slug").value("keyboard"));
    }

    @Test
    void softDelete_returns204AndProductDisappears() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(1L);
    }
}
