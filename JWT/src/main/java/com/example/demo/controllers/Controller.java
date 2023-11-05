package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dtos.ResponseDto;
import com.example.demo.dtos.SignUpRequestDto;
import com.example.demo.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@Tag(name = "Endpoints")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Returns user's details and a token")
    @PostMapping("sign-up")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseDto signUp(@Valid @RequestBody SignUpRequestDto request) {

        return userService.signUp(request);

    }

    @Operation(summary = "Get your user's details", description = "Returns your user's details and a new token")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("login")
    public ResponseDto login() {
        return userService.login();
    }

}
