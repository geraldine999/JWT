package com.example.demo.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponseDto implements Serializable {

    private static final long serialVersionUID = 1040847333177259165L;

    private List<ErrorDto> errors;

}
