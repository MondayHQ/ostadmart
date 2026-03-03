package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.CartItemEntity;
import com.example.ostadmart.dto.CartItemResponseDTO;

@Component
public class CartItemMapper {

    private final ModelMapper modelMapper;

    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemResponseDTO mapToResponseDTO(CartItemEntity cartItemEntity) {
        return modelMapper.map(cartItemEntity, CartItemResponseDTO.class);
    }

}
