package com.example.ragassistant.dto;

public record DocumentUploadResponse(Long documentId, String fileName, String status, int chunkCount) {
}
