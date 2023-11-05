package com.example.demo.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.PhoneDto;
import com.example.demo.dtos.SignUpRequestDto;
import com.example.demo.entities.PhoneEntity;
import com.example.demo.entities.UserEntity;

@Service
public class PhoneService {

    public Set<PhoneEntity> buildPhones(SignUpRequestDto request, UserEntity user) {
        Set<PhoneEntity> phones = new HashSet<>();

        for (PhoneDto p : request.getPhones()) {

            var phone = PhoneEntity.builder().cityCode(p.getCityCode()).countryCode(p.getCountryCode())
                    .number(p.getNumber()).user(user).build();

            phones.add(phone);
        }

        return phones;
    }

    public Set<PhoneDto> buildPhoneDtos(Set<PhoneEntity> phones) {

        Set<PhoneDto> phoneDtos = new HashSet<>();

        for (PhoneEntity p : phones) {
            var phoneDto = PhoneDto.builder().cityCode(p.getCityCode()).countryCode(p.getCountryCode())
                    .number(p.getNumber()).build();

            phoneDtos.add(phoneDto);
        }

        return phoneDtos;

    }

}
