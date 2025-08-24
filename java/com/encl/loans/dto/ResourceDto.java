package com.encl.loans.dto;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
public class ResourceDto {

//    @Schema(
//            description = "Status code in the response"
//    )
    private String status;

//    @Schema(
//            description = "Status message in the response"
//    )
    private Object message;
}
