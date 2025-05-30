package com.imedia24.productWatcher.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imedia24.productWatcher.core.constant.Constant;
import com.imedia24.productWatcher.entities.Action;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class ActionDto {
    private Long sku;
    private Action action;
    @JsonFormat(pattern = Constant.DATE_PATTERN)
    private LocalDate date;
}
