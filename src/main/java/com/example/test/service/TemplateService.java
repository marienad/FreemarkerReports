package com.example.test.service;

import com.example.test.controller.dto.ReportType;

import java.io.File;
import java.util.Map;

public interface TemplateService {

    File processTemplate(ReportType reports, String reportName, Map<String, Object> data);

    File getImage();

}
