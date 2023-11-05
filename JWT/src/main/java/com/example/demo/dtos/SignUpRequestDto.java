package com.example.demo.dtos;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import com.example.demo.validations.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Sign up request")
@Validated
public class SignUpRequestDto implements Serializable {

    private static final long serialVersionUID = -1585652983315788334L;

    @Schema(example = "Homero Simpson")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    @Schema(example = "homero@example.com.ar")
    private String email;
    @NotBlank(message = "password is required")
    @Password
    @Schema(example = "Password12")
    private String password;

    @Valid
    private Set<PhoneDto> phones;

}
