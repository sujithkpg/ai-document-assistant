# RAG Document Assistant API

Spring Boot backend starter for an AI-powered document assistant.

## Run

```bash
cd backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

## Current starter behavior

This version supports:

- PDF/TXT upload
- text extraction
- text chunking
- document listing
- mock chat response using retrieved chunks

Next enhancement:

- replace mock chat response with Spring AI/OpenAI
- add vector database: pgvector or Elasticsearch
