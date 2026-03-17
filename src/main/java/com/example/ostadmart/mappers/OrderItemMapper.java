package com.example.ostadmart.mappers;

import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Local Imports
import com.example.ostadmart.models.OrderItem;
import com.example.ostadmart.dto.OrderItemResponse;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {

    private final ModelMapper modelMapper;

    public OrderItemResponse mapToResponse(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemResponse.class);
    }

}
