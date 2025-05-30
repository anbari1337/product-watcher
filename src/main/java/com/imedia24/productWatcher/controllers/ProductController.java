package com.imedia24.productWatcher.controllers;

import com.imedia24.productWatcher.core.constant.Paths;
import com.imedia24.productWatcher.dtos.ActionsResponse;
import com.imedia24.productWatcher.dtos.PricingHistoryRequest;
import com.imedia24.productWatcher.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;


    @PostMapping(Paths.Product.ANALYZE_PRICING)
    public ResponseEntity<ActionsResponse> analyzePricing(@Valid @RequestBody PricingHistoryRequest pricingHistoryRequest){
        var pricing = productService.analyzeProductPricing(pricingHistoryRequest);
        return ResponseEntity.ok(pricing);
    }

}
