package com.encl.loans.controller;

import com.encl.loans.contants.LoansConstants;
import com.encl.loans.dto.LoansDto;
import com.encl.loans.dto.ResourceDto;
import com.encl.loans.service.ILoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
@Validated
public class LoansController {

    private ILoansService loansService;

    @PostMapping("/create")
    public ResponseEntity<ResourceDto> createLoan(@Valid @NotEmpty
                                                      @Pattern(regexp = "$|[0-9]{10}", message = "Number must have 10 digits")
                                                      String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResourceDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResourceDto> fetchLoan(@Valid @NotEmpty
                                                     @Pattern(regexp = "$|[0-9]{10}", message = "Number must have 10 digits") String mobileNumber) {

        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResourceDto(LoansConstants.STATUS_200, loansDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ResourceDto> updateLoan(@Valid @RequestBody LoansDto loansDto) {
        boolean updated = loansService.updateLoan(loansDto);
        if (updated)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResourceDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResourceDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResourceDto> deleteLoan(@Valid @NotEmpty
                                                      @Pattern(regexp = "$|[0-9]{10}", message = "Number must have 10 digits")
                                                      String mobileNumber) {
        boolean deleted = loansService.deleteLoan(mobileNumber);
        if (deleted)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResourceDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResourceDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
    }
}
