export interface DocumentSummary {
  documentId: number;
  fileName: string;
  status: string;
}

export interface DocumentUploadResponse {
  documentId: number;
  fileName: string;
  status: string;
  chunkCount: number;
}
