package com.campax.data;

import com.github.javafaker.Faker;
import com.campax.dto.request.ClassroomRequest;
import com.campax.entity.School;
import com.campax.enums.ClassroomStatus;
import com.campax.repository.ClassroomRepository;
import com.campax.repository.SchoolRepository;
import com.campax.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ClassroomData {
	
	private final ClassroomService classroomService;
	private final SchoolRepository schoolRepository;
	private final ClassroomRepository classroomRepository;
	
	//	@Bean
//	@Order(3)
	public CommandLineRunner seedClassrooms() {
		
		return args -> {
			Faker faker = new Faker();
			
			List<School> schools = schoolRepository.findAll();
			if (schools.isEmpty()) throw new RuntimeException("No schools found — seed schools first");
			
			String[] grades = {"Grade 1", "Grade 2", "Grade 3", "Grade 4",
				"Grade 5", "Grade 6", "Grade 7", "Grade 8"};
			
			for (School school : schools) {
				for (String grade : grades) {
					
					// Idempotent — skip if already exists for this school
					if (classroomRepository
						    .existsBySchoolIdAndClassroomCode(
							    school.getId(),
							    school.getSchoolCode() + "-" + grade.replace(" ", ""))) {
						continue;
					}
					
					ClassroomRequest request =
						ClassroomRequest.builder()
						                .classroomName(grade)
						                // unique per school: "GVS001-Grade1"
						                .classroomCode(
							                school
								                .getSchoolCode() + "-" + grade.replace(" ", ""))
						                .classroomStatus(ClassroomStatus.ACTIVE)
						                .build();
					
					classroomService.createClassroom(school.getId(), request);
				}
			}
		};
	}
	
}