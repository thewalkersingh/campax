package com.campax.data;

import com.github.javafaker.Faker;
import com.campax.dto.request.AddressRequest;
import com.campax.dto.request.ParentRequest;
import com.campax.dto.request.UserIdentityRequest;
import com.campax.entity.Parent;
import com.campax.entity.Student;
import com.campax.enums.Gender;
import com.campax.enums.ParentStatus;
import com.campax.repository.ParentRepository;
import com.campax.repository.StudentRepository;
import com.campax.service.ParentService;
import com.campax.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ParentData {
	
	private final ParentService parentService;
	private final ParentRepository parentRepository;
	private final StudentRepository studentRepository;
	private final StudentService studentService;
	
	//	@Bean
//	@Order(10)
	public CommandLineRunner seedParents() {
		return args -> {
			Faker faker = new Faker();
			
			int parentIndex = 0;
			
			// Seed 2 parents per student — one for each child roughly
			List<Student> students = studentRepository.findAll();
			if (students.isEmpty()) throw new RuntimeException("No students found — seed students first");
			
			// Create one parent per 2 students (realistic ratio)
			int totalParents = students.size() / 2;
			
			for (int i = 1; i <= totalParents; i++) {
				parentIndex++;
				
				// Deterministic — unique and idempotent
				String phone = String.format("700000%04d", parentIndex);
				String email = String.format("parent%04d@campax.com", parentIndex);
				
				// Idempotent — skip if already exists
				if (parentRepository.existsByIdentityPhone(phone)) continue;
				
				ParentRequest request = ParentRequest.builder()
					                        .occupation(faker.company()
						                                    .profession())
					                        .relation(faker.options()
						                                  .option("Father", "Mother", "Guardian"))
					                                  .parentStatus(ParentStatus.ACTIVE)
					                        .address(AddressRequest.builder()
						                                 .houseNumber(faker.address()
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
							                                          .option(Gender.MALE, Gender.FEMALE))
						                                  .build())
					                        .build();
				
				parentService.createParent(request);
			}
		};
	}
	
	//	@Bean
//	@Order(11)
	public CommandLineRunner seedStudentParentLinks() {
		return args -> {
			List<Student> students = studentRepository.findAll();
			List<Long> parentIds = parentRepository.findAll()
				                       .stream()
				                       .map(Parent::getId)
				                       .toList();
			
			if (parentIds.isEmpty()) throw new RuntimeException("No parents found — seed parents first");
			
			int parentIndex = 0;
			
			for (Student student : students) {
				// Skip if student already has a parent linked
				if (student.getParent() != null) continue;
				
				// Assign parents round-robin — each parent gets ~2 children
				Long parentId = parentIds.get(parentIndex % parentIds.size());
				parentIndex++;
				
				studentService.assignParent(student.getId(), parentId);
			}
		};
	}
	
}