package com.wassefchargui.department_service.service;

import com.wassefchargui.department_service.dto.DepartementRequestDto;
import com.wassefchargui.department_service.dto.DepartementResponseDto;

import java.util.List;

public interface DepartementService {

    DepartementResponseDto createDepartement(DepartementRequestDto requestDto);

    DepartementResponseDto getDepartementById(Long id);

    List<DepartementResponseDto> getAllDepartements();

    DepartementResponseDto updateDepartement(Long id, DepartementRequestDto requestDto);

    void deleteDepartement(Long id);

    DepartementResponseDto getDepartementByNom(String nom);
}
