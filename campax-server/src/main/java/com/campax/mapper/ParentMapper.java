package com.campax.mapper;

import com.campax.dto.common.StudentSummary;
import com.campax.dto.request.ParentRequest;
import com.campax.dto.response.ParentResponse;
import com.campax.entity.Parent;
import com.campax.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
	uses = {UserIdentityMapper.class, AddressMapper.class})
public interface ParentMapper {
	
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Parent toEntity(ParentRequest request);
	
	@Mapping(target = "students", ignore = true)
		// populated manually in service
	ParentResponse toResponse(Parent parent);
	
	@Mapping(target = "studentName",
		expression = "java(student.getIdentity().getFirstName() + ' ' + student" +
			             ".getIdentity().getLastName())")
	@Mapping(target = "phone", source = "identity.phone")
	@Mapping(target = "sectionName", source = "section.sectionName")
	@Mapping(target = "classroomName", source = "section.classroom.classroomName")
	StudentSummary toStudentSummary(Student student);
	
}
/*
Embedded Address → handled by AddressMapper.

Nested Identity → handled by UserIdentityMapper.

Children (Students) → handled by StudentMapper. MapStruct automatically maps lists (List<Student> ↔
List<StudentResponse>).
 */