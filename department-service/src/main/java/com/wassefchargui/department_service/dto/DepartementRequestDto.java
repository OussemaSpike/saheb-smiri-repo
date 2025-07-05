package com.wassefchargui.department_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating a department")
public class DepartementRequestDto {

    @Schema(description = "Department name", example = "Information Technology", required = true)
    @NotBlank(message = "Department name is required")
    private String nom;
}
