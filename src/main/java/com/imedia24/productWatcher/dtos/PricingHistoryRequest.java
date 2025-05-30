package com.imedia24.productWatcher.dtos;

import com.imedia24.productWatcher.entities.Product;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

@Data
public class PricingHistoryRequest {
    @Valid
    private List<Product> pricingHistory;
}
