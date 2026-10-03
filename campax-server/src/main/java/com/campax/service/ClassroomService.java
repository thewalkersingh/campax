package com.campax.service;

import com.campax.dto.request.ClassroomRequest;
import com.campax.dto.response.ClassroomResponse;
import com.campax.enums.ClassroomStatus;
import com.campax.wrapper.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ClassroomService {
	
	// ── Create / Update / Delete ──────────────────────────────────────────────
	ClassroomResponse createClassroom(Long schoolId, ClassroomRequest request);
	
	ClassroomResponse updateClassroom(Long classroomId, ClassroomRequest request);
	
	void deleteClassroom(Long classroomId);
	
	// ── Single fetch ──────────────────────────────────────────────────────────
	ClassroomResponse getClassroom(Long classroomId);
	
	ClassroomResponse getClassroomByCode(String classroomCode);
	
	// ── Lists ─────────────────────────────────────────────────────────────────
	PageResponse<ClassroomResponse> getAllClassrooms(Pageable pageable);
	
	PageResponse<ClassroomResponse> getClassroomsBySchool(Long schoolId, Pageable pageable);
	
	PageResponse<ClassroomResponse> getClassroomsBySchoolAndStatus(Long schoolId, ClassroomStatus classroomStatus,
		 Pageable pageable);
	
	// ── Status management ─────────────────────────────────────────────────────
	ClassroomResponse updateStatus(Long classroomId, ClassroomStatus classroomStatus);
	
}