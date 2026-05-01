package com.example.ragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingService {
    private static final int CHUNK_SIZE = 1200;
    private static final int OVERLAP = 200;

    public List<String> split(String text) {
        String normalized = text == null ? "" : text.replaceAll("\\s+", " ").trim();
        List<String> chunks = new ArrayList<>();

        if (normalized.isEmpty()) {
            return chunks;
        }

        int start = 0;
        while (start < normalized.length()) {
            int end = Math.min(start + CHUNK_SIZE, normalized.length());
            chunks.add(normalized.substring(start, end));

            if (end == normalized.length()) {
                break;
            }
            start = Math.max(0, end - OVERLAP);
        }
        return chunks;
    }
}
