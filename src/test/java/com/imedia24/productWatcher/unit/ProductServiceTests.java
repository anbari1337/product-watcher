package com.imedia24.productWatcher.unit;


import com.imedia24.productWatcher.core.constant.Constant;
import com.imedia24.productWatcher.dtos.ActionDto;
import com.imedia24.productWatcher.entities.Action;
import com.imedia24.productWatcher.entities.Product;
import com.imedia24.productWatcher.entities.ProductPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProductServiceTests {

    private final Product product = new Product(1L, "Iphone");


    @Test
    @DisplayName("Should sort dates and return a list of BUY and SELL actions achieving maximum profit")
    public void shouldReturnSortedBuyAndSellActionsForMaxProfit(){
        List<ProductPrice> prices = new ArrayList<>();

        prices.add(new ProductPrice(BigDecimal.valueOf(30), parseDate("03-11-2023")));
        prices.add(new ProductPrice(BigDecimal.valueOf(20), parseDate("02-11-2023")));
        prices.add(new ProductPrice(BigDecimal.valueOf(10), parseDate("01-11-2023")));

        product.setPrices(prices);
        List<ActionDto> actions = product.analyzePricing();

        Assertions.assertEquals(4, actions.size());
        Assertions.assertEquals(Action.BUY, actions.get(0).getAction());
        Assertions.assertEquals(Action.SELL, actions.get(1).getAction());
    }

    @Test
    @DisplayName("Should return an empty list if no profit can be made")
    public void shouldReturnEmptyActionListWhenPricesAreFlat(){
        List<ProductPrice> prices = new ArrayList<>();
        prices.add(new ProductPrice(BigDecimal.valueOf(10), parseDate("01-11-2023")));
        prices.add(new ProductPrice(BigDecimal.valueOf(10), parseDate("02-11-2023")));
        prices.add(new ProductPrice(BigDecimal.valueOf(10), parseDate("03-11-2023")));
        product.setPrices(prices);
        List<ActionDto> actions = product.analyzePricing();

        Assertions.assertTrue(actions.isEmpty());
    }

    @Test
    @DisplayName("Should return an empty list given an empty list of prices")
    public void shouldReturnEmptyActionListWhenPriceListIsEmpty(){
        List<ProductPrice> prices = Collections.emptyList();
        product.setPrices(prices);

        Assertions.assertTrue(product.analyzePricing().isEmpty());
    }


    private LocalDate parseDate(String date){
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constant.DATE_PATTERN);
        return LocalDate.from(dtf.parse(date));
    }

}
