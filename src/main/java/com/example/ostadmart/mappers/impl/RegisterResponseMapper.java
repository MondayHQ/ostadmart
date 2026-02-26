package com.example.ostadmart.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.mappers.Mapper;
import com.example.ostadmart.models.UserEntity;
import com.example.ostadmart.dto.RegisterResponseDTO;

@Component
public class RegisterResponseMapper implements Mapper<UserEntity, RegisterResponseDTO> {

    private final ModelMapper modelMapper;

    public RegisterResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity mapToEntity(RegisterResponseDTO registerResponseDTO) {
        return modelMapper.map(registerResponseDTO, UserEntity.class);
    }

    @Override
    public RegisterResponseDTO mapToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegisterResponseDTO.class);
    }

}
