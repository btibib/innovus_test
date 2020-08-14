package com.innovus.customException;

public class IncorrectAnswerException extends Exception {

    public IncorrectAnswerException(final String errorMessage) {
        super(errorMessage);
    }
}
