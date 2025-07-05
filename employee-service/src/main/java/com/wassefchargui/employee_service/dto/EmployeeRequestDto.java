package com.wassefchargui.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating an employee")
public class EmployeeRequestDto {

    @Schema(description = "Employee first name", example = "John", required = true)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "Employee last name", example = "Doe", required = true)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "Employee email address", example = "john.doe@company.com", required = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Employee position/job title", example = "Software Engineer", required = true)
    @NotBlank(message = "Position is required")
    private String position;

    @Schema(description = "Employee salary", example = "75000.00")
    @Positive(message = "Salary must be positive")
    private Double salary;

    @Schema(description = "Employee hire date", example = "2024-01-15")
    private LocalDate hireDate;

    @Schema(description = "Department ID the employee belongs to", example = "1", required = true)
    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
