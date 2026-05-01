package com.example.ragassistant.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class TextExtractionService {

    public String extract(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename().toLowerCase();

        if (fileName.endsWith(".pdf")) {
            try (PDDocument document = Loader.loadPDF(file.getBytes())) {
                return new PDFTextStripper().getText(document);
            }
        }

        if (fileName.endsWith(".txt")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        throw new IllegalArgumentException("Only PDF and TXT files are supported in the starter version.");
    }
}
