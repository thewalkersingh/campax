package com.campax.mapper;

import com.campax.dto.common.SubjectSummary;
import com.campax.dto.request.TeacherRequest;
import com.campax.dto.response.TeacherResponse;
import com.campax.entity.Subject;
import com.campax.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
	uses = {UserIdentityMapper.class, AddressMapper.class})
public interface TeacherMapper {
	
	@Mapping(target = "school", ignore = true)   // set manually in service
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Teacher toEntity(TeacherRequest request);
	
	@Mapping(target = "schoolId", source = "school.id")
	@Mapping(target = "schoolName", source = "school.schoolName")
	@Mapping(target = "subjects", source = "subjects")
		// needs custom method — see below
	TeacherResponse toResponse(Teacher teacher);
	
	// MapStruct uses this automatically for List<Subject> → List<SubjectSummary>
	@Mapping(target = "subjectName", source = "subjectName")
	@Mapping(target = "subjectCode", source = "subjectCode")
	SubjectSummary toSubjectSummary(Subject subject);
	
}