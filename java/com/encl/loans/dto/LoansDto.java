package com.encl.loans.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoansDto {

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    @PositiveOrZero
    private int totalLoan;

    @Positive
    private int amountPaid;

    private int outstandingAmount;

}
