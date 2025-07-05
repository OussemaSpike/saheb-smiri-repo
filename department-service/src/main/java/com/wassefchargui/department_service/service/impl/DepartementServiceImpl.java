package com.wassefchargui.department_service.service.impl;

import com.wassefchargui.department_service.dto.DepartementRequestDto;
import com.wassefchargui.department_service.dto.DepartementResponseDto;
import com.wassefchargui.department_service.entity.Departement;
import com.wassefchargui.department_service.exception.DepartementAlreadyExistsException;
import com.wassefchargui.department_service.exception.DepartementNotFoundException;
import com.wassefchargui.department_service.mapper.DepartementMapper;
import com.wassefchargui.department_service.repository.DepartementRepository;
import com.wassefchargui.department_service.service.DepartementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    @Override
    public DepartementResponseDto createDepartement(DepartementRequestDto requestDto) {
        log.info("Creating new department with name: {}", requestDto.getNom());

        if (departementRepository.existsByNom(requestDto.getNom())) {
            throw new DepartementAlreadyExistsException("Department with name '" + requestDto.getNom() + "' already exists");
        }

        Departement departement = departementMapper.toEntity(requestDto);
        Departement savedDepartement = departementRepository.save(departement);

        log.info("Department created successfully with id: {}", savedDepartement.getId());
        return departementMapper.toResponseDto(savedDepartement);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartementResponseDto getDepartementById(Long id) {
        log.info("Fetching department with id: {}", id);

        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new DepartementNotFoundException(id));

        return departementMapper.toResponseDto(departement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartementResponseDto> getAllDepartements() {
        log.info("Fetching all departments");

        List<Departement> departements = departementRepository.findAll();
        return departements.stream()
                .map(departementMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementResponseDto updateDepartement(Long id, DepartementRequestDto requestDto) {
        log.info("Updating department with id: {}", id);

        Departement existingDepartement = departementRepository.findById(id)
                .orElseThrow(() -> new DepartementNotFoundException(id));

        // Check if the new name already exists for a different department
        if (!existingDepartement.getNom().equals(requestDto.getNom()) &&
            departementRepository.existsByNom(requestDto.getNom())) {
            throw new DepartementAlreadyExistsException("Department with name '" + requestDto.getNom() + "' already exists");
        }

        departementMapper.updateEntityFromDto(requestDto, existingDepartement);
        Departement updatedDepartement = departementRepository.save(existingDepartement);

        log.info("Department updated successfully with id: {}", updatedDepartement.getId());
        return departementMapper.toResponseDto(updatedDepartement);
    }

    @Override
    public void deleteDepartement(Long id) {
        log.info("Deleting department with id: {}", id);

        if (!departementRepository.existsById(id)) {
            throw new DepartementNotFoundException(id);
        }

        departementRepository.deleteById(id);
        log.info("Department deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartementResponseDto getDepartementByNom(String nom) {
        log.info("Fetching department with name: {}", nom);

        Departement departement = departementRepository.findByNom(nom)
                .orElseThrow(() -> new DepartementNotFoundException("Department not found with name: " + nom));

        return departementMapper.toResponseDto(departement);
    }
}
