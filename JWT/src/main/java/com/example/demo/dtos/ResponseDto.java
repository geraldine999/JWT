package com.example.demo.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "User response")
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 7361243092950381631L;

    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    @JsonInclude(value = Include.NON_NULL)
    private String name;
    private String email;
    @JsonInclude(value = Include.NON_NULL)
    private String password;
    @JsonInclude(value = Include.NON_NULL)
    private Set<PhoneDto> phones;

}
