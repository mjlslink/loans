package com.encl.loans.exception;

import jakarta.validation.constraints.NotEmpty;

public class LoanExistsException extends RuntimeException {
    public LoanExistsException(@NotEmpty String s) {
        super(s);
    }
}
