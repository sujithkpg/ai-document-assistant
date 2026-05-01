import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentService } from '../../services/document.service';
import { DocumentUploadResponse } from '../../models/document.model';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload.component.html'
})
export class UploadComponent {
  selectedFile: File | null = null;
  isUploading = false;
  response: DocumentUploadResponse | null = null;
  errorMessage = '';

  constructor(private readonly documentService: DocumentService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] ?? null;
    this.response = null;
    this.errorMessage = '';
  }

  upload(): void {
    if (!this.selectedFile) {
      this.errorMessage = 'Please select a PDF or TXT file.';
      return;
    }

    this.isUploading = true;
    this.errorMessage = '';

    this.documentService.upload(this.selectedFile).subscribe({
      next: response => {
        this.response = response;
        this.isUploading = false;
      },
      error: error => {
        this.errorMessage = error?.error?.message ?? 'Document upload failed.';
        this.isUploading = false;
      }
    });
  }
}
