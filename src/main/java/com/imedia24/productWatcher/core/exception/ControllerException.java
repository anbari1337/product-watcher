package com.imedia24.productWatcher.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControllerException extends Exception {

    public ControllerException(String errorMessage) {
        super(errorMessage);
    }

}
