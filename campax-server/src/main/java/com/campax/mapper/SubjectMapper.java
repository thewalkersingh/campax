package com.campax.mapper;

import com.campax.dto.common.TeacherSummary;
import com.campax.dto.request.SubjectRequest;
import com.campax.dto.response.SubjectResponse;
import com.campax.entity.Subject;
import com.campax.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
	
	@Mapping(target = "teachers", source = "teachers")
	SubjectResponse toResponse(Subject subject);
	
	@Mapping(target = "teacherName", expression = "java(teacher.getIdentity().getFirstName() + ' ' + teacher" +
		                                              ".getIdentity().getLastName())")
	@Mapping(target = "phone", source = "identity.phone")
	TeacherSummary toTeacherSummary(Teacher teacher);
	
	@Mapping(target = "teachers", ignore = true)
	Subject toEntity(SubjectRequest request);
	
}