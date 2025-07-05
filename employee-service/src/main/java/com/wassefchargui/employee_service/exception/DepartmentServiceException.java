package com.wassefchargui.employee_service.exception;

public class DepartmentServiceException extends RuntimeException {

    public DepartmentServiceException(String message) {
        super(message);
    }

    public DepartmentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
