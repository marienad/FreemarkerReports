package com.example.test.service.impl;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.swing.AWTFontResolver;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static java.awt.Font.TRUETYPE_FONT;

@Slf4j
public class FileRenderer {

    public void htmlToPDFRender(String html, OutputStream os) {
        try (os) {
            final ITextRenderer iTextRenderer = new ITextRenderer();

            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            final ClassPathResource regular = new ClassPathResource("templates/Roboto-Regular.ttf");
            fontResolver.addFont(regular.getPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            iTextRenderer.setDocumentFromString(html);
            iTextRenderer.layout();
            iTextRenderer.createPDF(os, true);
        } catch (DocumentException | IOException e) {
            log.warn(e.getMessage());
        }
    }

    public void htmlToPNGRender(File html, OutputStream os) {
        try (os) {
            int width = 1024;
            int height = 1024;
            Java2DRenderer renderer = new Java2DRenderer(html, width, height);

            final ClassPathResource regular = new ClassPathResource("templates/Roboto-Regular.ttf");
            AWTFontResolver awtFontResolver = new AWTFontResolver();
            awtFontResolver.setFontMapping("roboto", Font.createFont(TRUETYPE_FONT, regular.getInputStream()));
            renderer.getSharedContext().setFontResolver(awtFontResolver);

            BufferedImage image = renderer.getImage();
            FSImageWriter writer = new FSImageWriter();
            writer.write(image, os);
        } catch (IOException | FontFormatException e) {
            log.warn(e.getMessage());
        }
    }
}
