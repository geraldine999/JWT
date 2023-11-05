package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Phone number")
public class PhoneDto implements Serializable {

    private static final long serialVersionUID = -3178216569895933515L;

    @NotNull(message = "number is required")
    @Schema(example = "45236784")
    private long number;
    @NotNull(message = "cityCode is required")
    @Schema(example = "11")
    private int cityCode;
    @NotBlank(message = "countryCode is required")
    @Schema(example = "+54 9")
    private String countryCode;

}
