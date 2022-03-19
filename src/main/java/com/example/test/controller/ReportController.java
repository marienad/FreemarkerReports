package com.example.test.controller;

import com.example.test.controller.dto.ReportType;
import com.example.test.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/reports")
@Tag(name = "Отчеты")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<FileSystemResource> getMeterReportAll(Integer size, ReportType reportType) {
        log.info("GET /report/{id}/all: id={}, reportType={}", size, reportType.name());

        var file = reportService.getReportFull(size, reportType);
        var reportName = "report-" + size + (reportType.equals(ReportType.CSV) ? ".csv" : ".pdf");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + reportName)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType((reportType.equals(ReportType.CSV) ?
                        "text/csv;charset=UTF-8" : "application/pdf")))
                .body(new FileSystemResource(file));
    }

}
