package com.example.demo.stock.infra.adapters.api;


import com.example.demo.stock.domain.ProductChangeRequest;
import com.example.demo.stock.infra.adapters.mappers.ProductMapper;
import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;
import com.example.demo.stock.infra.dto.ProductPatchRequest;
import com.example.demo.stock.ports.in.ProductServicePort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServicePort productService;
    private final ProductMapper productMapper;

    public ProductController(ProductServicePort productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ProductPage searchProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return productService.searchProducts(new SearchProductRequest(searchTerm, category, minPrice, maxPrice, page, size, sort, direction));
    }

    @PatchMapping("/{id}")
    public void patchProduct(@PathVariable Integer id,@RequestBody ProductPatchRequest request) {
       ProductChangeRequest changeRequest =  productMapper.toProductChangeRequest(id,request);
       productService.partialUpdate(changeRequest);

    }

}
