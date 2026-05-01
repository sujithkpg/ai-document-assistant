package com.example.ragassistant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ai_document_chunk")
public class DocumentChunkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentEntity document;

    @Column(nullable = false)
    private Integer chunkIndex;

    @Lob
    @Column(nullable = false)
    private String chunkText;

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
}
