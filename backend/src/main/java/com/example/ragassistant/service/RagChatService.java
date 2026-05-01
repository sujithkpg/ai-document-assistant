package com.example.ragassistant.service;

import com.example.ragassistant.dto.ChatResponse;
import com.example.ragassistant.dto.SourceReference;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagChatService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagChatService(VectorStore vectorStore,
                          ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public ChatResponse ask(String question) {
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(5)
                        .similarityThreshold(0.60)
                        .build()
        );

        if (similarDocuments == null || similarDocuments.isEmpty()) {
            return new ChatResponse(
                    "I could not find relevant information in the uploaded document.",
                    List.of()
            );
        }

        String context = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n---\n\n"));

        String answer = chatClient.prompt()
                .system("""
                        You are an AI document assistant.
                        Answer the user's question using only the provided context.
                        If the answer is not available in the context, say:
                        "I could not find this information in the uploaded document."
                        Do not invent answers.
                        """)
                .user("""
                        Context:
                        %s
                        
                        Question:
                        %s
                        """.formatted(context, question))
                .call()
                .content();

        List<SourceReference> sources = similarDocuments.stream()
                .map(document -> new SourceReference(
                        String.valueOf(document.getMetadata().get("fileName")),
                        document.getMetadata().get("chunkIndex"),
                        document.getText()
                ))
                .toList();

        return new ChatResponse(answer, sources);
    }
}