package com.campax.service.impl;

import com.campax.dto.request.StaffRequest;
import com.campax.dto.response.StaffResponse;
import com.campax.entity.School;
import com.campax.entity.Staff;
import com.campax.enums.Department;
import com.campax.enums.StaffRole;
import com.campax.enums.StaffStatus;
import com.campax.mapper.StaffMapper;
import com.campax.repository.SchoolRepository;
import com.campax.repository.StaffRepository;
import com.campax.service.StaffService;
import com.campax.wrapper.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
	
	private final StaffRepository staffRepository;
	private final StaffMapper staffMapper;
	private final SchoolRepository schoolRepository;
	
	// ── Create / Update / Delete ──────────────────────────────────────────────
	public StaffResponse createStaff(Long schoolId, StaffRequest request) {
		
		if (staffRepository.existsByIdentityPhone(request.getIdentity().getPhone()))
			throw new IllegalStateException("Phone already registered");
		if (staffRepository.existsByIdentityEmail(request.getIdentity().getEmail()))
			throw new IllegalStateException("Email already registered");
		School school = schoolRepository
			                .findById(schoolId)
			                .orElseThrow(() -> new IllegalStateException("School not found"));
		Staff staff = staffMapper.toEntity(request);
		staff.setSchool(school);
		staffRepository.save(staff);
		return staffMapper.toResponse(staff);
	}
	
	public StaffResponse updateStaff(Long staffId, StaffRequest request) {
		
		staffRepository.findById(staffId)
		               .orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
		Staff staff = staffMapper.toEntity(request);
		staffRepository.save(staff);
		return staffMapper.toResponse(staff);
		
	}
	
	public void deleteStaff(Long staffId) {
		
		Staff staff = staffRepository
			              .findById(staffId)
			              .orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
		staff.setStaffStatus(StaffStatus.DELETED);
		staffRepository.save(staff);
	}
	
	// ── Single fetch ──────────────────────────────────────────────────────────
	public StaffResponse getStaff(Long staffId) {
		
		return staffRepository
			       .findById(staffId)
			       .map(staffMapper::toResponse)
			       .orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
	}
	
	public StaffResponse getStaffByPhone(String phone) {
		
		return staffRepository
			       .findByIdentityPhone(phone)
			       .map(staffMapper::toResponse)
			       .orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
	}
	
	public StaffResponse getStaffByEmail(String email) {
		
		return staffRepository
			       .findByIdentityEmail(email)
			       .map(staffMapper::toResponse)
			       .orElseThrow(() -> new EntityNotFoundException("Staff Not Found"));
	}
	
	// ── Lists ─────────────────────────────────────────────────────────────────
	public PageResponse<StaffResponse> getAllStaff(Pageable pageable) {
		
		Page<Staff> page = staffRepository.findAll(pageable);
		List<StaffResponse> response = page.getContent()
		                                   .stream()
		                                   .map(staffMapper::toResponse)
		                                   .toList();
		return PageResponse.<StaffResponse>builder()
		                   .data(response)
		                   .page(page.getNumber())
		                   .size(page.getSize())
		                   .totalElements(page.getTotalElements())
		                   .totalPages(page.getTotalPages())
		                   .hasNext(page.hasNext())
		                   .hasPrevious(page.hasPrevious())
		                   .build();
	}
	
	public PageResponse<StaffResponse> getStaffBySchool(Long schoolId, Pageable pageable) {
		
		Page<Staff> page = staffRepository.findBySchoolId(schoolId, pageable);
		List<StaffResponse> responses = page.getContent()
		                                    .stream()
		                                    .map(staffMapper::toResponse)
		                                    .toList();
		return PageResponse.<StaffResponse>builder()
		                   .data(responses)
		                   .page(page.getNumber())
		                   .size(page.getSize())
		                   .totalElements(page.getTotalElements())
		                   .totalPages(page.getTotalPages())
		                   .hasNext(page.hasNext())
		                   .hasPrevious(page.hasPrevious())
		                   .build();
	}
	
	public PageResponse<StaffResponse> getStaffBySchoolAndStaffStatus(Long schoolId, StaffStatus status,
		Pageable pageable) {
		
		Page<Staff> page = staffRepository.findBySchoolIdAndStaffStatus(schoolId, status, pageable);
		List<StaffResponse> responses = page.getContent()
		                                    .stream()
		                                    .map(staffMapper::toResponse)
		                                    .toList();
		return PageResponse.<StaffResponse>builder()
		                   .data(responses)
		                   .page(page.getNumber())
		                   .size(page.getSize())
		                   .totalElements(page.getTotalElements())
		                   .totalPages(page.getTotalPages())
		                   .hasNext(page.hasNext())
		                   .hasPrevious(page.hasPrevious())
		                   .build();
	}
	
	// Filter by role — e.g. all LIBRARIAN staff in a school
	public List<StaffResponse> getStaffByStaffRole(Long schoolId, StaffRole staffRole) {
		
		return staffRepository.findBySchoolIdAndStaffRole(schoolId, staffRole)
		                      .stream()
		                      .map(staffMapper::toResponse)
		                      .toList();
	}
	
	// Filter by department — e.g. all FINANCE department staff
	public List<StaffResponse> getStaffByDepartment(Long schoolId, Department department) {
		
		return staffRepository.findBySchoolIdAndDepartment(schoolId, department)
		                      .stream()
		                      .map(staffMapper::toResponse)
		                      .toList();
	}
	
	// ── Search ────────────────────────────────────────────────────────────────
	public PageResponse<StaffResponse> searchStaffByName(Long schoolId, String name, Pageable pageable) {
		
		Page<Staff> page = staffRepository.searchByNameAndSchoolId(schoolId, name, pageable);
		List<StaffResponse> responses = page.getContent()
		                                    .stream()
		                                    .map(staffMapper::toResponse)
		                                    .toList();
		return PageResponse.<StaffResponse>builder()
		                   .data(responses)
		                   .page(page.getNumber())
		                   .size(page.getSize())
		                   .totalElements(page.getTotalElements())
		                   .totalPages(page.getTotalPages())
		                   .hasNext(page.hasNext())
		                   .hasPrevious(page.hasPrevious())
		                   .build();
	}
	
	// ── Status management ─────────────────────────────────────────────────────
	public StaffResponse updateStatus(Long staffId, StaffStatus status) {
		
		Staff staff = staffRepository
			              .findById(staffId)
			              .orElseThrow(() -> new RuntimeException("Teacher not found"));
		staff.setStaffStatus(status);
		staffRepository.save(staff);
		return staffMapper.toResponse(staff);
	}
	
	// ── Stats ─────────────────────────────────────────────────────────────────
	// Count of staff per role in a school — useful for admin dashboard
	public long countBySchoolAndStaffRole(Long schoolId, StaffRole staffRole) {
		
		return staffRepository.countBySchoolIdAndStaffRole(schoolId, staffRole);
	}
	
}