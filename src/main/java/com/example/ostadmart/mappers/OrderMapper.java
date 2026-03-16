package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.Order;
import com.example.ostadmart.dto.OrderResponse;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderResponse mapToResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

}
