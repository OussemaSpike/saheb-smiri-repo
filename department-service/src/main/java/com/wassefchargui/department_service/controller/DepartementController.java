package com.wassefchargui.department_service.controller;

import com.wassefchargui.department_service.dto.DepartementRequestDto;
import com.wassefchargui.department_service.dto.DepartementResponseDto;
import com.wassefchargui.department_service.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departements")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Department", description = "Department management APIs")
public class DepartementController {

    private final DepartementService departementService;

    @PostMapping
    @Operation(
        summary = "Create a new department",
        description = "Creates a new department with the provided information. Department name must be unique."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Department created successfully",
                content = @Content(schema = @Schema(implementation = DepartementResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Department with this name already exists")
    })
    public ResponseEntity<DepartementResponseDto> createDepartement(
            @Parameter(description = "Department information", required = true)
            @Valid @RequestBody DepartementRequestDto requestDto) {
        log.info("REST request to create department: {}", requestDto.getNom());
        DepartementResponseDto responseDto = departementService.createDepartement(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get department by ID",
        description = "Retrieves a department by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Department found",
                content = @Content(schema = @Schema(implementation = DepartementResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<DepartementResponseDto> getDepartementById(
            @Parameter(description = "Department ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("REST request to get department by id: {}", id);
        DepartementResponseDto responseDto = departementService.getDepartementById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    @Operation(
        summary = "Get all departments",
        description = "Retrieves a list of all departments in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of departments retrieved successfully")
    })
    public ResponseEntity<List<DepartementResponseDto>> getAllDepartements() {
        log.info("REST request to get all departments");
        List<DepartementResponseDto> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a department",
        description = "Updates an existing department with new information. Department name must be unique."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Department updated successfully",
                content = @Content(schema = @Schema(implementation = DepartementResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "409", description = "Department with this name already exists")
    })
    public ResponseEntity<DepartementResponseDto> updateDepartement(
            @Parameter(description = "Department ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated department information", required = true)
            @Valid @RequestBody DepartementRequestDto requestDto) {
        log.info("REST request to update department with id: {}", id);
        DepartementResponseDto responseDto = departementService.updateDepartement(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a department",
        description = "Deletes a department by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<Void> deleteDepartement(
            @Parameter(description = "Department ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("REST request to delete department with id: {}", id);
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name/{nom}")
    @Operation(
        summary = "Get department by name",
        description = "Retrieves a department by its name"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Department found",
                content = @Content(schema = @Schema(implementation = DepartementResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<DepartementResponseDto> getDepartementByNom(
            @Parameter(description = "Department name", required = true, example = "IT")
            @PathVariable String nom) {
        log.info("REST request to get department by name: {}", nom);
        DepartementResponseDto responseDto = departementService.getDepartementByNom(nom);
        return ResponseEntity.ok(responseDto);
    }
}
