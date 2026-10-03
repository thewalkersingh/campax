package com.campax.dto.response;

import com.campax.dto.common.StaffSummary;
import com.campax.enums.Department;
import com.campax.enums.StaffRole;
import com.campax.enums.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffResponse {
	
	private StaffStatus status;
	private Department department;
	private StaffRole staffRole;
	private Long schoolId;
	private String schoolName;
	private StaffSummary staffSummary;
	private LocalDate joiningDate;
	private String photoUrl;
	private AddressResponse address;
	private UserIdentityResponse identity;
	
}