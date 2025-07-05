package com.wassefchargui.department_service.exception;

public class DepartementNotFoundException extends RuntimeException {

    public DepartementNotFoundException(String message) {
        super(message);
    }

    public DepartementNotFoundException(Long id) {
        super("Department not found with id: " + id);
    }
}
