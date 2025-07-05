package com.wassefchargui.employee_service.mapper;

import com.wassefchargui.employee_service.dto.EmployeeRequestDto;
import com.wassefchargui.employee_service.dto.EmployeeResponseDto;
import com.wassefchargui.employee_service.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "department", ignore = true)
    EmployeeResponseDto toResponseDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EmployeeRequestDto requestDto, @MappingTarget Employee employee);
}
