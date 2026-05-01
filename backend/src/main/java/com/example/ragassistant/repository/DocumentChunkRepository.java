package com.example.ragassistant.repository;

import com.example.ragassistant.entity.DocumentChunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunkEntity, Long> {
    List<DocumentChunkEntity> findByDocumentIdOrderByChunkIndexAsc(Long documentId);
}
