package com.encl.loans.service.impl;

import com.encl.loans.contants.LoansConstants;
import com.encl.loans.dto.LoansDto;
import com.encl.loans.entities.Loans;
import com.encl.loans.exception.LoanExistsException;
import com.encl.loans.exception.PaymentException;
import com.encl.loans.exception.ResourceNotFoundException;
import com.encl.loans.mapper.LoansMapper;
import com.encl.loans.repository.LoansRepository;
import com.encl.loans.service.ILoansService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loansRepository.findByMobileNumber(mobileNumber);
        if(loans.isPresent()) {
            throw new LoanExistsException("Loan details already exists for mobile number: " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));

    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan details not found for mobile number: " + mobileNumber)
        );
        return LoansMapper.mapToDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan details not found for mobile number: " + loansDto.getMobileNumber())
        );
        //is the amount paid more than total loan?

        //process payment and update outstandingAmount
        if (loans.getAmountPaid() > 0) {
            int totalPaid = loans.getAmountPaid() + loansDto.getAmountPaid();
            loansDto.setAmountPaid(totalPaid);
        }

        //The loan type cannot be changed once created
        if (!loansDto.getLoanType().equals(loans.getLoanType())) {
            throw new PaymentException("Loan type cannot be changed - if you want a new loan please create it");
        }

        if (loansDto.getOutstandingAmount() != loans.getOutstandingAmount()) {
            throw new PaymentException("Outstanding amount cannot be changed directly - it is calculated based on total loan and amount paid");
        }

        if(loans.getOutstandingAmount() == 0) {
            throw new PaymentException("Loan is already paid off - payment not required");
        }

        if (loansDto.getAmountPaid() > loans.getTotalLoan()) {
            throw new PaymentException("Amount paid cannot be more than total loan");
        }

        int payment = loansDto.getTotalLoan() - loansDto.getAmountPaid();
        loansDto.setOutstandingAmount(payment);

        LoansMapper.mapToEntity(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(@NotEmpty String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan details not found for mobile number: " + mobileNumber)
        );
        loansRepository.delete(loans);
        return true;
    }
}
