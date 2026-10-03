package com.campax.mapper;

import com.campax.dto.request.ClassroomRequest;
import com.campax.dto.response.ClassroomResponse;
import com.campax.entity.Classroom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
	
	Classroom toEntity(ClassroomRequest request);
	
	ClassroomResponse toResponse(Classroom classroom);
	
}