package com.example.ostadmart.mappers;

public interface Mapper<A, B> {

    A mapToEntity(B b);

    B mapToDTO(A a);

}
