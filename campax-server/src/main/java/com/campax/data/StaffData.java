package com.campax.data;

import com.github.javafaker.Faker;
import com.campax.dto.request.AddressRequest;
import com.campax.dto.request.StaffRequest;
import com.campax.dto.request.UserIdentityRequest;
import com.campax.entity.School;
import com.campax.enums.Department;
import com.campax.enums.Gender;
import com.campax.enums.StaffRole;
import com.campax.enums.StaffStatus;
import com.campax.repository.SchoolRepository;
import com.campax.repository.StaffRepository;
import com.campax.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StaffData {
	
	private final StaffService staffService;
	private final StaffRepository staffRepository;
	private final SchoolRepository schoolRepository;
	
	//	@Bean
//	@Order(12)
	public CommandLineRunner seedStaff() {
		
		return args -> {
			Faker faker = new Faker();
			
			List<School> schools = schoolRepository.findAll();
			if (schools.isEmpty()) throw new RuntimeException("No schools found — seed schools first");
			
			// One staff member per role per school — realistic and bounded
			record RoleConfig(StaffRole role, Department department) {
			
			}
			
			List<RoleConfig> roleConfigs = List.of(
				new RoleConfig(StaffRole.ADMIN, Department.ADMINISTRATION),
				new RoleConfig(StaffRole.ACCOUNTANT, Department.FINANCE),
				new RoleConfig(StaffRole.LIBRARIAN, Department.LIBRARY),
				new RoleConfig(StaffRole.LAB_ASSISTANT, Department.SCIENCE),
				new RoleConfig(StaffRole.DRIVER, Department.TRANSPORT),
				new RoleConfig(StaffRole.SECURITY_GUARD, Department.SECURITY)
			);
			
			int staffIndex = 0;
			
			for (School school : schools) {
				for (RoleConfig config : roleConfigs) {
					staffIndex++;
					
					// Deterministic — unique and idempotent
					String phone = String.format("600000%04d", staffIndex);
					String email = String.format("staff%04d@campax.com", staffIndex);
					
					// Idempotent — skip if already exists
					if (staffRepository.existsByIdentityPhone(phone)) continue;
					
					StaffRequest request = StaffRequest.builder()
					                                   .staffRole(config.role())
					                                   .department(config.department()).staffStatus(StaffStatus.ACTIVE)
					                                   .qualification(faker.educator().campus())
					                                   .experience(faker.number().numberBetween(1, 20))
					                                   .joiningDate(LocalDate.of(
						                                   faker.number().numberBetween(2010, 2023),
						                                   faker.number().numberBetween(1, 12),
						                                   faker.number().numberBetween(1, 28)))
					                                   .dob(LocalDate.of(
						                                   faker.number().numberBetween(1970, 1995),
						                                   faker.number().numberBetween(1, 12),
						                                   faker.number().numberBetween(1, 28)))
					                                   .photoUrl(faker.internet().avatar())
					                                   .address(AddressRequest.builder()
					                                                          .houseNumber(faker.address().buildingNumber())
					                                                          .streetName(faker.address().streetName())
					                                                          .zipCode(faker.address().zipCode())
					                                                          .city(faker.address().city())
					                                                          .state(faker.address().state())
					                                                          .build())
					                                   .identity(UserIdentityRequest.builder()
					                                                                .firstName(faker.name().firstName())
					                                                                .lastName(faker.name().lastName())
					                                                                .phone(phone)
					                                                                .email(email)
					                                                                .gender(faker.options()
					                                                                             .option(Gender.MALE,
						                                                                             Gender.FEMALE))
					                                                                .build())
					                                   .build();
					
					staffService.createStaff(school.getId(), request);
				}
			}
		};
	}
	
}