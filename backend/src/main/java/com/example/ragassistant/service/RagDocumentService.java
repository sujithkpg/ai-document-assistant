package com.example.ragassistant.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RagDocumentService {

    private final VectorStore vectorStore;
    private final TextExtractionService textExtractionService;

    public RagDocumentService(VectorStore vectorStore,
                              TextExtractionService textExtractionService) {
        this.vectorStore = vectorStore;
        this.textExtractionService = textExtractionService;
    }

    public void processDocument(MultipartFile file) {
        String text = textExtractionService.extractText(file);

        if (text == null || text.isBlank()) {
            throw new RuntimeException("No text could be extracted from the uploaded document.");
        }

        List<String> chunks = splitText(text, 1000, 150);

        List<Document> documents = new ArrayList<>();

        for (int i = 0; i < chunks.size(); i++) {
            documents.add(Document.builder()
                    .text(chunks.get(i))
                    .metadata(Map.of(
                            "fileName", file.getOriginalFilename() == null ? "unknown" : file.getOriginalFilename(),
                            "chunkIndex", i
                    ))
                    .build());
        }

        vectorStore.add(documents);
    }

    private List<String> splitText(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();

        int start = 0;

        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));

            if (end == text.length()) {
                break;
            }

            start = end - overlap;
        }

        return chunks;
    }
}