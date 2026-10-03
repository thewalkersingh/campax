package com.campax.data;

import com.github.javafaker.Faker;
import com.campax.dto.request.AddressRequest;
import com.campax.dto.request.StudentRequest;
import com.campax.dto.request.UserIdentityRequest;
import com.campax.entity.Section;
import com.campax.enums.Gender;
import com.campax.enums.StudentStatus;
import com.campax.repository.SectionRepository;
import com.campax.repository.StudentRepository;
import com.campax.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StudentData {
	
	private final StudentService studentService;
	private final SectionRepository sectionRepository;
	private final StudentRepository studentRepository;
	
	//	@Bean
//	@Order(9)
	public CommandLineRunner seedStudents() {
		return args -> {
			Faker faker = new Faker();
			
			List<Section> sections = sectionRepository.findAllWithClassroom();
			if (sections.isEmpty()) throw new RuntimeException("No sections found — seed sections first");
			
			int studentIndex = 0;
			
			for (Section section : sections) {
				for (int i = 1; i <= 5; i++) {
					studentIndex++;
					
					// Deterministic — unique and idempotent
					String phone = String.format("800000%04d", studentIndex);
					String email = String.format("student%04d@campax.com", studentIndex);
					
					// Idempotent — skip if already exists
					if (studentRepository.existsByIdentityPhone(phone)) continue;
					
					// Varied dob based on grade
					String classroomName = section.getClassroom()
						                       .getClassroomName();
					int grade = Integer.parseInt(classroomName.split(" ")[1]);
					int birthYear = 2024 - (grade + 5);  // Grade 1 → born ~2018, Grade 8 → born ~2011
					
					StudentRequest request =
						StudentRequest.builder()
							.dob(LocalDate.of(birthYear, faker.number()
								                             .numberBetween(1, 12),
								faker.number()
									.numberBetween(1, 28)))
							.admissionDate(LocalDate.now())
							.photoUrl(faker.internet()
								          .avatar())
							.studentStatus(StudentStatus.ACTIVE)
							.guardianName(faker.name()
								              .fullName())
							.guardianRelation("Guardian")
							.address(AddressRequest.builder()
								         .houseNumber(
									         faker.address()
										         .buildingNumber())
								         .streetName(faker.address()
									                     .streetName())
								         .zipCode(faker.address()
									                  .zipCode())
								         .city(faker.address()
									               .city())
								         .state(faker.address()
									                .state())
								         .build())
							.identity(UserIdentityRequest.builder()
								          .firstName(faker.name()
									                     .firstName())
								          .lastName(faker.name()
									                    .lastName())
								          .phone(phone)
								          .email(email)
								          .gender(faker.options()
									                  .option(Gender.MALE,
										                  Gender.FEMALE))
								          .build())
							.build();
					
					studentService.createStudent(section.getId(), request);
				}
			}
		};
	}
	
}