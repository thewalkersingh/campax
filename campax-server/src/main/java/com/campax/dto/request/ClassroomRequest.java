package com.campax.dto.request;

import com.campax.enums.ClassroomStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomRequest {
	
	@NotNull
	@Size(max = 100)
	private String classroomName;
	
	@NotNull
	@Size(max = 50)
	private String classroomCode;
	
	@NotNull
	private ClassroomStatus classroomStatus;
	
	@NotNull
	private Long schoolId;
	
}