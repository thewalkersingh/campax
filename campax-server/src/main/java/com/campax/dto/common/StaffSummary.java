package com.campax.dto.common;

import com.campax.enums.Department;
import com.campax.enums.StaffRole;
import com.campax.enums.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffSummary {
	
	private Long id;
	private String staffName;      // firstName + lastName from identity
	private String phone;
	private Department role;
	private StaffRole staffRole;
	private Department department;
	private StaffStatus staffStatus;
	
}