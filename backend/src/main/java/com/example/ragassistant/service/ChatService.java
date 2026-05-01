package com.example.ragassistant.service;

import com.example.ragassistant.dto.ChatRequest;
import com.example.ragassistant.dto.ChatResponse;
import com.example.ragassistant.dto.SourceReference;
import com.example.ragassistant.entity.DocumentChunkEntity;
import com.example.ragassistant.exception.ResourceNotFoundException;
import com.example.ragassistant.repository.DocumentChunkRepository;
import com.example.ragassistant.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;

    public ChatResponse ask(ChatRequest request) {
        if (!documentRepository.existsById(request.documentId())) {
            throw new ResourceNotFoundException("Document not found for id: " + request.documentId());
        }

        List<DocumentChunkEntity> chunks = chunkRepository.findByDocumentIdOrderByChunkIndexAsc(request.documentId());
        if (chunks.isEmpty()) {
            return new ChatResponse("No document content is available for this document.", List.of());
        }

        List<DocumentChunkEntity> matchedChunks = findRelevantChunks(request.question(), chunks);

        String answer = buildMockAnswer(request.question(), matchedChunks);
        List<SourceReference> sources = matchedChunks.stream()
                .map(chunk -> new SourceReference(chunk.getId(), chunk.getChunkIndex(), trim(chunk.getChunkText(), 600)))
                .toList();

        return new ChatResponse(answer, sources);
    }

    private List<DocumentChunkEntity> findRelevantChunks(String question, List<DocumentChunkEntity> chunks) {
        String[] keywords = question.toLowerCase().split("\\W+");
        return chunks.stream()
                .sorted(Comparator.comparingInt((DocumentChunkEntity chunk) -> score(chunk.getChunkText(), keywords)).reversed())
                .limit(3)
                .toList();
    }

    private int score(String text, String[] keywords) {
        String lower = text.toLowerCase();
        int score = 0;
        for (String keyword : keywords) {
            if (keyword.length() > 2 && lower.contains(keyword)) {
                score++;
            }
        }
        return score;
    }

    private String buildMockAnswer(String question, List<DocumentChunkEntity> matchedChunks) {
        String context = matchedChunks.stream()
                .map(DocumentChunkEntity::getChunkText)
                .findFirst()
                .orElse("");

        return "Starter AI response: I found related document content for your question: '" + question + "'. "
                + "In the next step, replace this mock response with an OpenAI/Spring AI call using the retrieved chunks as context. "
                + "Top matching context preview: " + trim(context, 350);
    }

    private String trim(String value, int max) {
        if (value == null || value.length() <= max) {
            return value;
        }
        return value.substring(0, max) + "...";
    }
}
