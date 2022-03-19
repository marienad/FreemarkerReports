package com.example.test.service.impl;

import com.example.test.controller.dto.PeriodicityCompactDto;
import com.example.test.controller.dto.ReportType;
import com.example.test.service.ReportService;
import com.example.test.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final TemplateService templateService;

    @Autowired
    public ReportServiceImpl(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public File getReportFull(Integer size, ReportType reportType) {
        Map<String, Object> data = new HashMap<>();
        var records = getRecords(size);
        if (reportType.equals(ReportType.CSV)) {
            data.put("csvRecords", records);
        } else {
            data.put("isExist", true);
            data.put("meterId", "meterId");
            data.put("records", records);
            byte[] fileContent = null;
            try {
                ClassPathResource image = new ClassPathResource("templates/smile.png");
                fileContent = image.getInputStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!records.isEmpty()) {
                var dates = records.stream()
                        .map(PeriodicityCompactDto::getDate)
                        .sorted().collect(Collectors.toList());
                data.put("dateFrom", dates.get(0));
                data.put("dateTo", dates.get(dates.size() - 1));

                try {
                    fileContent = new FileSystemResource(templateService.getImage()).getInputStream().readAllBytes();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            data.put("imagePath", encodedString);
        }
        var reportName = "report-" + size + (reportType.equals(ReportType.CSV) ? ".csv" : ".pdf");
        return templateService.processTemplate(reportType, reportName, data);
    }

    private List<PeriodicityCompactDto> getRecords(int size) {
        List<PeriodicityCompactDto> list = new ArrayList<>();
        var indicator = (int) (Math.random() * 10);
        var date = LocalDate.of(2022, 6, 1);
        for (int i = 0; i < size; i++) {
            list.add(PeriodicityCompactDto.builder()
                    .deviceId("id")
                    .lastIndicator(indicator)
                    .average(BigDecimal.valueOf(Math.random() * 15))
                    .date(date)
                    .lastTime(LocalTime.of(10, 0))
                    .build());
            indicator += (int) (Math.random() * 10);
            date = date.plusDays(1);
        }
        return list;
    }
}
