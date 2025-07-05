package com.wassefchargui.department_service.mapper;

import com.wassefchargui.department_service.dto.DepartementRequestDto;
import com.wassefchargui.department_service.dto.DepartementResponseDto;
import com.wassefchargui.department_service.entity.Departement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartementMapper {

    DepartementResponseDto toResponseDto(Departement departement);

    @Mapping(target = "id", ignore = true)
    Departement toEntity(DepartementRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(DepartementRequestDto requestDto, @MappingTarget Departement departement);
}
