package com.example.demo.services;

import java.time.LocalDateTime;


import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dtos.ResponseDto;
import com.example.demo.dtos.PhoneDto;
import com.example.demo.dtos.SignUpRequestDto;
import com.example.demo.entities.PhoneEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptions.SignUpException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PhoneService phoneService;

    public ResponseDto signUp(SignUpRequestDto request) {

        validateEmailDoesNotAlreadyExist(request.getEmail());

        var user = buildUser(request);

        Set<PhoneEntity> phones = phoneService.buildPhones(request, user);

        user.setPhones(phones);
        userRepository.save(user);

        String token = jwtService.createJwt(user);

        return ResponseDto.builder().email(user.getEmail()).name(user.getName()).phones(request.getPhones())
                .created(user.getCreated()).id(user.getUuid()).isActive(user.isActive()).lastLogin(null).token(token)
                .build();

    }

    private UserEntity buildUser(SignUpRequestDto request) {
        String encodedPassword = encoder.encode(request.getPassword());

        return UserEntity.builder().email(request.getEmail()).name(request.getName()).created(LocalDateTime.now())
                .uuid(UUID.randomUUID()).password(encodedPassword).isActive(true).build();
    }

    private void validateEmailDoesNotAlreadyExist(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new SignUpException("You have already signed up.");
        }

    }

    public ResponseDto login() {

        var user = getLoggedInUser();
        updateLastLogin(user);

        Set<PhoneDto> phones = phoneService.buildPhoneDtos(user.getPhones());

        return ResponseDto.builder().created(user.getCreated()).email(user.getEmail()).id(user.getUuid())
                .isActive(user.isActive()).lastLogin(user.getLastLogin()).name(user.getName())
                .password(user.getPassword()).phones(phones).token(jwtService.createJwt(user)).build();

    }

    private UserEntity getLoggedInUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void updateLastLogin(UserEntity user) {

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found"));

    }

}
