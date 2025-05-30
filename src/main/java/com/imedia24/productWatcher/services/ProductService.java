package com.imedia24.productWatcher.services;

import com.imedia24.productWatcher.dtos.ActionsResponse;
import com.imedia24.productWatcher.dtos.PricingHistoryRequest;
import com.imedia24.productWatcher.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public ActionsResponse analyzeProductPricing(PricingHistoryRequest request) {
        var actionsResponse = new ActionsResponse();

        for (Product product : request.getPricingHistory()) {
            var actions = product.analyzePricing();
            actionsResponse.setActions(actions);
        }

        return actionsResponse;
    }

}
