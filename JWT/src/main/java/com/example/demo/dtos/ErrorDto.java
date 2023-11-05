package com.example.demo.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "Error")
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 6799097308672681642L;

    private String detail;
    private int code;
    private Timestamp timestamp;

}
