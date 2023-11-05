package com.example.demo.security.jwt;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Validated
public class JwtProperties {

    @NotBlank
    private String prefix;
    @NotBlank
    private String key;

}
