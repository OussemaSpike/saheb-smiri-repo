package com.wassefchargui.employee_service.service;

import com.wassefchargui.employee_service.dto.EmployeeRequestDto;
import com.wassefchargui.employee_service.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

    EmployeeResponseDto getEmployeeById(Long id);

    List<EmployeeResponseDto> getAllEmployees();

    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);

    void deleteEmployee(Long id);

    List<EmployeeResponseDto> getEmployeesByDepartmentId(Long departmentId);

    List<EmployeeResponseDto> searchEmployeesByName(String name);

    EmployeeResponseDto getEmployeeByEmail(String email);
}
