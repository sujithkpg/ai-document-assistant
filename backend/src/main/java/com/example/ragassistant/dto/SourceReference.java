package com.example.ragassistant.dto;

public record SourceReference(
        String fileName,
        Object chunkIndex,
        String content
) {
}