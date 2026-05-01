package com.example.ragassistant.dto;

import java.util.List;

public record ChatResponse(String answer, List<SourceReference> sources) {
}
