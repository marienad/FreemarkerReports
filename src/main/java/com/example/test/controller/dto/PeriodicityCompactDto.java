package com.example.test.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Частота снятия показаний (для отчетов)")
public class PeriodicityCompactDto {

    @Schema(description = "Идентификатор устройства")
    private String deviceId;

    @Schema(description = "Дата")
    private LocalDate date;

    @Schema(description = "Среднее время между показаниями(в секундах)")
    private BigDecimal average;

    @Schema(description = "Последний показатель")
    private Integer lastIndicator;

    @Schema(description = "Последнее время снятия показаний")
    private LocalTime lastTime;
}
