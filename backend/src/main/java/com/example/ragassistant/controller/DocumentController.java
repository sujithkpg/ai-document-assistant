package com.example.ragassistant.controller;

import com.example.ragassistant.dto.DocumentSummaryResponse;
import com.example.ragassistant.service.DocumentService;
import com.example.ragassistant.service.RagDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final RagDocumentService ragDocumentService;

    @GetMapping
    public ResponseEntity<List<DocumentSummaryResponse>> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        ragDocumentService.processDocument(file);
        return ResponseEntity.ok("Document uploaded and indexed successfully.");
    }
}
