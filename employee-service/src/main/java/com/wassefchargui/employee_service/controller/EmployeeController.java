package com.wassefchargui.employee_service.controller;

import com.wassefchargui.employee_service.dto.EmployeeRequestDto;
import com.wassefchargui.employee_service.dto.EmployeeResponseDto;
import com.wassefchargui.employee_service.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Employee", description = "Employee management APIs with department integration")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(
        summary = "Create a new employee",
        description = "Creates a new employee with the provided information. Email must be unique and department must exist in the Department Service."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employee created successfully",
                content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "409", description = "Employee with this email already exists"),
        @ApiResponse(responseCode = "503", description = "Department service unavailable")
    })
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Parameter(description = "Employee information", required = true)
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        log.info("REST request to create employee: {}", requestDto.getEmail());
        EmployeeResponseDto responseDto = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get employee by ID",
        description = "Retrieves an employee by their unique identifier, including their department information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee found",
                content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @Parameter(description = "Employee ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("REST request to get employee by id: {}", id);
        EmployeeResponseDto responseDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    @Operation(
        summary = "Get all employees",
        description = "Retrieves a list of all employees in the system with their department information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully")
    })
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        log.info("REST request to get all employees");
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update an employee",
        description = "Updates an existing employee with new information. Email must be unique and department must exist."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Employee or department not found"),
        @ApiResponse(responseCode = "409", description = "Employee with this email already exists"),
        @ApiResponse(responseCode = "503", description = "Department service unavailable")
    })
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @Parameter(description = "Employee ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated employee information", required = true)
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        log.info("REST request to update employee with id: {}", id);
        EmployeeResponseDto responseDto = employeeService.updateEmployee(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete an employee",
        description = "Deletes an employee by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "Employee ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("REST request to delete employee with id: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{departmentId}")
    @Operation(
        summary = "Get employees by department",
        description = "Retrieves all employees belonging to a specific department"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of employees in the department"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "503", description = "Department service unavailable")
    })
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartmentId(
            @Parameter(description = "Department ID", required = true, example = "1")
            @PathVariable Long departmentId) {
        log.info("REST request to get employees by department id: {}", departmentId);
        List<EmployeeResponseDto> employees = employeeService.getEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    @Operation(
        summary = "Search employees by name",
        description = "Searches for employees by first name or last name (case-insensitive partial match)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of matching employees")
    })
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployeesByName(
            @Parameter(description = "Name to search for (first or last name)", required = true, example = "John")
            @RequestParam String name) {
        log.info("REST request to search employees by name: {}", name);
        List<EmployeeResponseDto> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/by-email/{email}")
    @Operation(
        summary = "Get employee by email",
        description = "Retrieves an employee by their email address"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee found",
                content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(
            @Parameter(description = "Employee email", required = true, example = "john.doe@company.com")
            @PathVariable String email) {
        log.info("REST request to get employee by email: {}", email);
        EmployeeResponseDto responseDto = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(responseDto);
    }
}
