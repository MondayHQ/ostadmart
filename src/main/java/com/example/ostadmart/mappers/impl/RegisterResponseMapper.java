package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.User;
import com.example.ostadmart.dto.RegisterResponse;

@Component
public class RegisterResponseMapper implements Mapper<User, RegisterResponse> {

    private final ModelMapper modelMapper;

    public RegisterResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User mapToEntity(RegisterResponse registerResponse) {
        return modelMapper.map(registerResponse, User.class);
    }

    @Override
    public RegisterResponse mapToDTO(User user) {
        return modelMapper.map(user, RegisterResponse.class);
    }

}
