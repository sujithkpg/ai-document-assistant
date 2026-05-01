package com.example.ragassistant.service;

import com.example.ragassistant.dto.DocumentSummaryResponse;
import com.example.ragassistant.dto.DocumentUploadResponse;
import com.example.ragassistant.entity.DocumentChunkEntity;
import com.example.ragassistant.entity.DocumentEntity;
import com.example.ragassistant.entity.DocumentStatus;
import com.example.ragassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final TextExtractionService textExtractionService;
    private final ChunkingService chunkingService;

    @Transactional
    public DocumentUploadResponse upload(MultipartFile file) {
        try {
            String text = textExtractionService.extract(file);
            List<String> chunks = chunkingService.split(text);

            DocumentEntity document = new DocumentEntity();
            document.setFileName(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setStatus(DocumentStatus.PROCESSED);

            for (int i = 0; i < chunks.size(); i++) {
                DocumentChunkEntity chunk = new DocumentChunkEntity();
                chunk.setDocument(document);
                chunk.setChunkIndex(i + 1);
                chunk.setChunkText(chunks.get(i));
                document.getChunks().add(chunk);
            }

            DocumentEntity saved = documentRepository.save(document);
            return new DocumentUploadResponse(saved.getId(), saved.getFileName(), saved.getStatus().name(), chunks.size());
        } catch (Exception ex) {
            throw new IllegalStateException("Document processing failed: " + ex.getMessage(), ex);
        }
    }

    public List<DocumentSummaryResponse> findAll() {
        return documentRepository.findAll().stream()
                .map(doc -> new DocumentSummaryResponse(doc.getId(), doc.getFileName(), doc.getStatus().name()))
                .toList();
    }
}
