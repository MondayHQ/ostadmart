package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.CartItem;
import com.example.ostadmart.dto.CartItemResponse;

@Component
public class CartItemMapper {

    private final ModelMapper modelMapper;

    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartItemResponse mapToResponseDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemResponse.class);
    }

}
