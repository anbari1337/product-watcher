package com.imedia24.productWatcher.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imedia24.productWatcher.core.constant.Constant;
import com.imedia24.productWatcher.core.constant.ErrorConstant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPrice {
    @NotNull(message = ErrorConstant.E_PRICE_REQUIRED)
    @Min(value = 0, message = ErrorConstant.E_NEGATIVE_PRICE_ERROR)
    private BigDecimal price;
    @JsonFormat(pattern = Constant.DATE_PATTERN)
    private LocalDate date;
}
