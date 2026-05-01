package com.example.ragassistant.controller;

import com.example.ragassistant.dto.ChatRequest;
import com.example.ragassistant.dto.ChatResponse;
import com.example.ragassistant.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponse> ask(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.ask(request));
    }
}
