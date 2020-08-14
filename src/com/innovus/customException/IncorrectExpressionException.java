package com.innovus.customException;

public class IncorrectExpressionException extends Exception {

    public IncorrectExpressionException(final String errorMsg) {
        super(errorMsg);
    }
}
