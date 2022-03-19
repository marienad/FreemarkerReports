package com.example.test.service.impl;

import com.example.test.controller.dto.ReportType;
import com.example.test.service.TemplateService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final FileRenderer fileRenderer;

    @Autowired
    public TemplateServiceImpl(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.fileRenderer = new FileRenderer();
    }

    @Override
    public File processTemplate(ReportType reportType, String reportName, Map<String, Object> data) {
        File file = new File(reportName);
        file.deleteOnExit();
        switch (reportType) {
            case CSV:
                try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate("csv.ftl");
                    template.process(data, writer);
                } catch (IOException | TemplateException e) {
                    log.warn(e.getMessage());
                }
                break;
            case PDF:
                try {
                    StringWriter stringWriter = new StringWriter();
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate("pdf.ftl");
                    template.process(data, stringWriter);
                    String html = stringWriter.toString();
                    OutputStream os = new FileOutputStream(file);
                    fileRenderer.htmlToPDFRender(html, os);
                } catch (IOException | TemplateException e) {
                    log.warn(e.getMessage());
                }
                break;
        }
        return file;
    }

    @Override
    public File getImage() {
        File image = new File("image.png");
        image.deleteOnExit();
        File file = new File("image.html");
        file.deleteOnExit();
        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("image.ftl");
            template.process(null, writer);
            OutputStream os = new FileOutputStream(image);
            fileRenderer.htmlToPNGRender(file, os);
        } catch (IOException | TemplateException e) {
            log.warn(e.getMessage());
        }
        return image;
    }

}
