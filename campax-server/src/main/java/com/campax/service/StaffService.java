package com.campax.service;

import com.campax.dto.request.StaffRequest;
import com.campax.dto.response.StaffResponse;
import com.campax.enums.Department;
import com.campax.enums.StaffRole;
import com.campax.enums.StaffStatus;
import com.campax.wrapper.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
	
	// ── Create / Update / Delete ──────────────────────────────────────────────
	StaffResponse createStaff(Long schoolId, StaffRequest request);
	
	StaffResponse updateStaff(Long staffId, StaffRequest request);
	
	void deleteStaff(Long staffId);                          // soft delete → DELETED status
	
	// ── Single fetch ──────────────────────────────────────────────────────────
	StaffResponse getStaff(Long staffId);
	
	StaffResponse getStaffByPhone(String phone);
	
	StaffResponse getStaffByEmail(String email);
	
	// ── Lists ─────────────────────────────────────────────────────────────────
	PageResponse<StaffResponse> getAllStaff(Pageable pageable);
	
	PageResponse<StaffResponse> getStaffBySchool(Long schoolId, Pageable pageable);
	
	PageResponse<StaffResponse> getStaffBySchoolAndStaffStatus(Long schoolId, StaffStatus staffStatus,
		Pageable pageable);
	
	// Filter by role — e.g. all LIBRARIAN staff in a school
	List<StaffResponse> getStaffByStaffRole(Long schoolId, StaffRole staffRole);
	
	// Filter by department — e.g. all FINANCE department staff
	List<StaffResponse> getStaffByDepartment(Long schoolId, Department department);
	
	// ── Search ────────────────────────────────────────────────────────────────
	PageResponse<StaffResponse> searchStaffByName(Long schoolId, String name, Pageable pageable);
	
	// ── Status management ─────────────────────────────────────────────────────
	StaffResponse updateStatus(Long staffId, StaffStatus staffStatus);
	
	// ── Stats ─────────────────────────────────────────────────────────────────
	// Count of staff per role in a school — useful for admin dashboard
	long countBySchoolAndStaffRole(Long schoolId, StaffRole staffRole);
	
}