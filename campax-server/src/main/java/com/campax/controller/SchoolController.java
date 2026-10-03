package com.campax.controller;

import com.campax.dto.request.SchoolRequest;
import com.campax.dto.response.SchoolResponse;
import com.campax.enums.SchoolStatus;
import com.campax.exception.ResourceNotFoundException;
import com.campax.service.SchoolService;
import com.campax.wrapper.ApiResponse;
import com.campax.wrapper.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/schools")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "School Management", description = "Endpoints for managing schools")
public class SchoolController {
	
	private final SchoolService schoolService;
	
	// ── Create a new school ────────────────────────────────────────────────
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<SchoolResponse> createSchool(@RequestBody SchoolRequest request) {
		log.info("Creating school with name {}", request.getSchoolName());
		SchoolResponse response = schoolService.createSchool(request);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School created successfully")
								.data(response)
								.statusCode(201)
								.build();
	}
	
	// ── Update school details ─────────────────────────────────────────────
	@PatchMapping("/{id}/request")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<SchoolResponse> updateSchool(@PathVariable Long id, @RequestBody SchoolRequest request) {
		log.info("Updating school with id {}", id);
		SchoolResponse response = schoolService.updateSchool(id, request);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School Updated Successfully")
								.data(response)
								.statusCode(200)
								.build();
	}
	
	// ── Delete a school ───────────────────────────────────────────────────
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<String> deleteSchool(@PathVariable Long id) {
		log.info("Deleting school with id {}", id);
		schoolService.deleteSchool(id);
		return ApiResponse.<String>builder()
								.success(true)
								.message("School deleted successfully")
								.data("Deleted school with id: " + id)
								.statusCode(200)
								.build();
	}
	
	// ── Get school by ID ──────────────────────────────────────────────────
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<SchoolResponse> getSchool(@PathVariable Long id) {
		log.info("Getting school with id {}", id);
		SchoolResponse response = schoolService.getSchool(id);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School by ID fetched successfully")
								.data(response)
								.build();
	}
	
	@GetMapping("/code/{schoolCode}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<SchoolResponse> getSchoolByCode(@PathVariable String schoolCode) {
		log.info("Getting school with code {}", schoolCode);
		if (!schoolService.existsByCode(schoolCode))
			throw new ResourceNotFoundException("School with code " + schoolCode + " not found");
		SchoolResponse response = schoolService.getSchoolByCode(schoolCode);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School by SchoolCode fetched successfully")
								.data(response)
								.build();
	}
	
	@GetMapping("/email/{email}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<SchoolResponse> getSchoolByEmail(@PathVariable String email) {
		log.info("Getting school with email {}", email);
		if (!schoolService.existsByEmail(email))
			throw new ResourceNotFoundException("School with email " + email + " not found");
		SchoolResponse response = schoolService.getSchoolByEmail(email);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School by Email fetched successfully")
								.data(response)
								.build();
	}
	
	@GetMapping("/phone/{phone}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<SchoolResponse> getSchoolByPhone(@PathVariable String phone) {
		log.info("Getting school with phone {}", phone);
		SchoolResponse response = schoolService.getSchoolByPhone(phone);
		return ApiResponse.<SchoolResponse>builder()
								.success(true)
								.message("School by Phone fetched successfully")
								.data(response)
								.build();
	}
	
	// ── Get all schools ───────────────────────────────────────────────────
	@GetMapping("/public")
	public ApiResponse<PageResponse<SchoolResponse>> getPublicSchools(Pageable pageable) {
		log.info("Getting public schools");
		PageResponse<SchoolResponse> response = schoolService.getPublicSchools(pageable);
		return ApiResponse.<PageResponse<SchoolResponse>>builder()
								.success(true)
								.message("Schools fetched successfully")
								.data(response)
								.build();
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<PageResponse<SchoolResponse>> getAllSchools(Pageable pageable) {
		log.info("Getting all schools");
		PageResponse<SchoolResponse> response = schoolService.getAllSchools(pageable);
		return ApiResponse.<PageResponse<SchoolResponse>>builder()
								.success(true)
								.message("Schools fetched successfully")
								.data(response)
								.build();
	}
	
	@GetMapping("/status/{status}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<PageResponse<SchoolResponse>> getSchoolsByStatus(@PathVariable SchoolStatus status,
		Pageable pageable) {
		log.info("Getting schools by status {}", status);
		PageResponse<SchoolResponse> response =
			schoolService.getSchoolsBySchoolStatus(status, pageable);
		return ApiResponse.<PageResponse<SchoolResponse>>builder()
								.data(response)
								.success(true)
								.message("School by Status fetched Successfully")
								.build();
	}
	
	@GetMapping("/name/{name}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STAFF')")
	public ApiResponse<PageResponse<SchoolResponse>> searchSchoolsByName(@PathVariable String name, Pageable pageable) {
		log.info("Searching schools by name {}", name);
		PageResponse<SchoolResponse> response = schoolService.searchSchoolsByName(name, pageable);
		return ApiResponse.<PageResponse<SchoolResponse>>builder()
								.data(response)
								.success(true)
								.message("School by Name fetched Successfully")
								.build();
	}
	
	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<SchoolResponse> updateSchoolStatus(@PathVariable Long id,
		@RequestBody SchoolStatus status) {
		log.info("Updating school status for school with id {}", id);
		SchoolResponse response = schoolService.updateStatus(id, status);
		return ApiResponse.<SchoolResponse>builder()
								.data(response)
								.success(true)
								.message("School Status Updated Successfully")
								.build();
	}
	
}