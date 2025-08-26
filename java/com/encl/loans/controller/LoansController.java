package com.encl.loans.controller;

import com.encl.loans.contants.LoansConstants;
import com.encl.loans.dto.ErrorResponseDto;
import com.encl.loans.dto.LoansContactInfoDto;
import com.encl.loans.dto.LoansDto;
import com.encl.loans.dto.ResourceDto;
import com.encl.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Crud REST APIs for Loans",
        description = "CRUD REST APIs for CREATE< READ, UPDATE and DELETE account details"
)
@RestController
@RequestMapping("/api/loans")
@Validated
public class LoansController {

    private final ILoansService loansService;

    public LoansController(ILoansService iLoansService) {
        this.loansService = iLoansService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto contactInfoDto;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Loan"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuidInfo() {
        return ResponseEntity.status(HttpStatus.OK).body("Build Version: " + buildVersion);
    }

    @Operation(
            summary = "Java version",
            description = "REST API to fetch the Java version the service uses"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Contact Info",
            description = "REST API to fetch the contact information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
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
