package com.example.ctf_kt_0.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ответ при ошибке")
public class ErrorResponseDTO {

    @Schema(description = "HTTP статус-код", example = "400")
    private int status;

    @Schema(description = "Сообщение об ошибке", example = "Invalid ID")
    private String message;
}
