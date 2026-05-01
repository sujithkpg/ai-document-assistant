import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentService } from '../../services/document.service';
import { ChatService } from '../../services/chat.service';
import { DocumentSummary } from '../../models/document.model';
import { ChatResponse } from '../../models/chat.model';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html'
})
export class ChatComponent implements OnInit {
  documents: DocumentSummary[] = [];
  selectedDocumentId: number | null = null;
  question = '';
  response: ChatResponse | null = null;
  isLoading = false;
  errorMessage = '';

  constructor(
    private readonly documentService: DocumentService,
    private readonly chatService: ChatService
  ) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments(): void {
    this.documentService.findAll().subscribe({
      next: documents => {
        this.documents = documents;
        this.selectedDocumentId = documents[0]?.documentId ?? null;
      },
      error: error => this.errorMessage = error?.error?.message ?? 'Unable to load documents.'
    });
  }

  ask(): void {
    if (!this.selectedDocumentId) {
      this.errorMessage = 'Please upload/select a document first.';
      return;
    }

    if (!this.question.trim()) {
      this.errorMessage = 'Please enter a question.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.response = null;

    this.chatService.ask({
      documentId: this.selectedDocumentId,
      question: this.question.trim()
    }).subscribe({
      next: response => {
        this.response = response;
        this.isLoading = false;
      },
      error: error => {
        this.errorMessage = error?.error?.message ?? 'Chat request failed.';
        this.isLoading = false;
      }
    });
  }
}
