package com.encl.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//@Schema(
//        name = "ErrorReponse",
//        description = "Error rsponse information"
//)
@Data @AllArgsConstructor
public class ErrorResponseDto {
//    @Schema(description = "API Path invoked by the customer")
    private String apiPath;

//    @Schema(description = "Error code of the operation")
    private HttpStatus errorCode;

//    @Schema(description = "Error message to be displayed")
    private String errorMessage;

//    @Schema(description = "Time the error occured")
    private LocalDateTime errorTime;
}
