package com.wassefchargui.employee_service.service.impl;

import com.wassefchargui.employee_service.client.DepartmentClient;
import com.wassefchargui.employee_service.dto.DepartmentDto;
import com.wassefchargui.employee_service.dto.EmployeeRequestDto;
import com.wassefchargui.employee_service.dto.EmployeeResponseDto;
import com.wassefchargui.employee_service.entity.Employee;
import com.wassefchargui.employee_service.exception.DepartmentNotFoundException;
import com.wassefchargui.employee_service.exception.DepartmentServiceException;
import com.wassefchargui.employee_service.exception.EmployeeAlreadyExistsException;
import com.wassefchargui.employee_service.exception.EmployeeNotFoundException;
import com.wassefchargui.employee_service.mapper.EmployeeMapper;
import com.wassefchargui.employee_service.repository.EmployeeRepository;
import com.wassefchargui.employee_service.service.EmployeeService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentClient departmentClient;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        log.info("Creating new employee with email: {}", requestDto.getEmail());

        if (employeeRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with email '" + requestDto.getEmail() + "' already exists");
        }

        validateDepartmentExists(requestDto.getDepartmentId());

        Employee employee = employeeMapper.toEntity(requestDto);
        if (employee.getHireDate() == null) {
            employee.setHireDate(LocalDate.now());
        }

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Employee created successfully with id: {}", savedEmployee.getId());
        return buildEmployeeResponse(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return buildEmployeeResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        log.info("Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::buildEmployeeResponse)
                .toList();
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        log.info("Updating employee with id: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        if (!existingEmployee.getEmail().equals(requestDto.getEmail()) &&
            employeeRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with email '" + requestDto.getEmail() + "' already exists");
        }

        if (!existingEmployee.getDepartmentId().equals(requestDto.getDepartmentId())) {
            validateDepartmentExists(requestDto.getDepartmentId());
        }

        employeeMapper.updateEntityFromDto(requestDto, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        log.info("Employee updated successfully with id: {}", updatedEmployee.getId());
        return buildEmployeeResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByDepartmentId(Long departmentId) {
        log.info("Fetching employees for department id: {}", departmentId);

        validateDepartmentExists(departmentId);

        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream()
                .map(this::buildEmployeeResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> searchEmployeesByName(String name) {
        log.info("Searching employees by name: {}", name);

        List<Employee> employees = employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

        return employees.stream()
                .map(this::buildEmployeeResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByEmail(String email) {
        log.info("Fetching employee with email: {}", email);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with email: " + email));

        return buildEmployeeResponse(employee);
    }

    private void validateDepartmentExists(Long departmentId) {
        try {
            log.info("Validating department exists with id: {}", departmentId);
            departmentClient.getDepartmentById(departmentId);
        } catch (FeignException.NotFound ex) {
            log.error("Department not found with id: {}", departmentId);
            throw new DepartmentNotFoundException(departmentId);
        } catch (FeignException ex) {
            log.error("Error communicating with department service: {}", ex.getMessage());
            throw new DepartmentServiceException("Unable to validate department. Please try again later.", ex);
        }
    }

    private EmployeeResponseDto buildEmployeeResponse(Employee employee) {
        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(employee);

        try {
            DepartmentDto department = departmentClient.getDepartmentById(employee.getDepartmentId());
            responseDto.setDepartment(department);
        } catch (FeignException ex) {
            log.warn("Could not fetch department information for employee {}: {}", employee.getId(), ex.getMessage());
        }

        return responseDto;
    }
}
