package com.wassefchargui.employee_service.client;

import com.wassefchargui.employee_service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service", url = "${department.service.url:http://localhost:8080}")
public interface DepartmentClient {

    @GetMapping("/api/v1/departements/{id}")
    DepartmentDto getDepartmentById(@PathVariable Long id);
}
