package com.campax.dto.response;

import com.campax.dto.common.SubjectSummary;
import com.campax.enums.TeacherStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponse {
	
	private Long id;
	private String qualification;
	private Integer experience;
	private String photoUrl;
	private LocalDate dob;
	private LocalDate joiningDate;
	private AddressResponse address;   // embedded DTO
	private TeacherStatus teacherStatus;
	private Long schoolId;
	private String schoolName;
	private UserIdentityResponse identity;  // nested DTO
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<SubjectSummary> subjects;
	
}