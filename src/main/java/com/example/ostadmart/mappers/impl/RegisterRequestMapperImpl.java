package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.dto.RegisterRequestDTO;

@Component
public class RegisterRequestMapperImpl implements Mapper<UserEntity, RegisterRequestDTO> {

    private final ModelMapper modelMapper;

    public RegisterRequestMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity mapToEntity(RegisterRequestDTO registerRequestDTO) {
        return modelMapper.map(registerRequestDTO, UserEntity.class);
    }

    @Override
    public RegisterRequestDTO mapToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegisterRequestDTO.class);
    }

}
