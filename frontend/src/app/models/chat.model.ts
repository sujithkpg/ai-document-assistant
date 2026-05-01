export interface ChatRequest {
  documentId: number;
  question: string;
}

export interface SourceReference {
  chunkId: number;
  chunkIndex: number;
  chunkText: string;
}

export interface ChatResponse {
  answer: string;
  sources: SourceReference[];
}
