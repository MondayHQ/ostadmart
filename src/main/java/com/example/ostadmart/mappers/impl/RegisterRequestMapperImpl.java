package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.User;
import com.example.ostadmart.dto.RegisterRequest;

@Component
public class RegisterRequestMapperImpl implements Mapper<User, RegisterRequest> {

    private final ModelMapper modelMapper;

    public RegisterRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User mapToEntity(RegisterRequest registerRequest) {
        return modelMapper.map(registerRequest, User.class);
    }

    @Override
    public RegisterRequest mapToDTO(User user) {
        return modelMapper.map(user, RegisterRequest.class);
    }

}
