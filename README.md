# AI Document Assistant (RAG-based)

This project is an AI-powered document assistant that allows users to upload documents and ask questions based on their content.

It uses Retrieval-Augmented Generation (RAG) to provide accurate, context-aware answers by combining semantic search with large language models.

## 🚀 Features

- Upload and process documents (PDF, text)
- Automatic text extraction and chunking
- Embedding generation for semantic search
- Vector database integration
- AI-powered question answering
- Source-based responses (grounded answers)
- REST API with Spring Boot
- Angular-based UI

## 🧠 Architecture

Angular UI → Spring Boot API → Vector DB → LLM API

## 🛠 Tech Stack

- Backend: Java, Spring Boot, Spring AI
- Frontend: Angular
- AI: OpenAI API
- Vector DB: PostgreSQL (pgvector) / Elasticsearch
- Database: PostgreSQL

## 🎯 Use Cases

- Internal knowledge assistant
- API documentation assistant
- Support/helpdesk automation
- Enterprise document search

## 🔐 Future Improvements

- Authentication & authorization
- AI response validation layer
- Prompt security (injection protection)
- Cost and token monitoring
- Logging and observability