package com.campax.mapper;

import com.campax.dto.request.SchoolRequest;
import com.campax.dto.response.SchoolResponse;
import com.campax.entity.School;
import org.mapstruct.Mapper;

@Mapper(
	 componentModel = "spring",
	 uses = {TeacherMapper.class, ClassroomMapper.class})
public interface SchoolMapper {
	
	School toEntity(SchoolRequest request);
	
	SchoolResponse toResponse(School school);
	
}