package com.wassefchargui.employee_service.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(Long departmentId) {
        super("Department not found with id: " + departmentId);
    }
}
