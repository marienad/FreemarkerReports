package com.example.test.service;

import com.example.test.controller.dto.ReportType;
import org.springframework.ui.Model;

import java.io.File;

public interface ReportService {

    File getReportFull(Integer size, ReportType reportType);
}
