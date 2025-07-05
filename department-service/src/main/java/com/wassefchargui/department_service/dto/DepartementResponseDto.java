package com.wassefchargui.department_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response DTO containing department information")
public class DepartementResponseDto {

    @Schema(description = "Department unique identifier", example = "1")
    private Long id;

    @Schema(description = "Department name", example = "Information Technology")
    private String nom;
}
