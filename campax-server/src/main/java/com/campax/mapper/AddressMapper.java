package com.campax.mapper;

import com.campax.dto.request.AddressRequest;
import com.campax.dto.response.AddressResponse;
import com.campax.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	
	Address toEntity(AddressRequest request);
	
	AddressResponse toResponse(Address entity);
	
}