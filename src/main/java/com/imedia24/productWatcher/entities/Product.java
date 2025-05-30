package com.imedia24.productWatcher.entities;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.dtos.ActionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @NotNull(message = ErrorConstant.E_PRODUCT_SKU_REQUIRED)
    private final Long sku;

    @NotBlank(message =  ErrorConstant.E_PRODUCT_NAME_BLANK)
    @NotEmpty(message = ErrorConstant.E_PRODUCT_NAME_REQUIRED)
    private final String name;

    @Valid
    private List<ProductPrice> prices;



    public List<ActionDto> analyzePricing(){
        if(prices.isEmpty()) return Collections.emptyList();

        List<ActionDto> actions = new ArrayList<>();
        prices.sort(Comparator.comparing(ProductPrice::getDate));

        int size = prices.size();
        for (int i = 0; i < size; i++) {
            if(i + 1 < size &&
                    prices.get(i + 1).getPrice().compareTo(prices.get(i).getPrice()) > 0
            ){
                actions.add(new ActionDto(this.getSku(), Action.BUY, prices.get(i).getDate()));
                actions.add(new ActionDto(this.getSku(), Action.SELL, prices.get(i + 1).getDate()));
            }
        }

        return actions;
    }


}
