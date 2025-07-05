package com.wassefchargui.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response DTO containing complete employee information with department details")
public class EmployeeResponseDto {

    @Schema(description = "Employee unique identifier", example = "1")
    private Long id;

    @Schema(description = "Employee first name", example = "John")
    private String firstName;

    @Schema(description = "Employee last name", example = "Doe")
    private String lastName;

    @Schema(description = "Employee email address", example = "john.doe@company.com")
    private String email;

    @Schema(description = "Employee position/job title", example = "Software Engineer")
    private String position;

    @Schema(description = "Employee salary", example = "75000.00")
    private Double salary;

    @Schema(description = "Employee hire date", example = "2024-01-15")
    private LocalDate hireDate;

    @Schema(description = "Department ID the employee belongs to", example = "1")
    private Long departmentId;

    @Schema(description = "Complete department information fetched from Department Service")
    private DepartmentDto department;
}
