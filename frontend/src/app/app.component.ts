import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="app-shell">
      <header class="header">
        <div>
          <h1>RAG Document Assistant</h1>
          <p>Upload documents and ask AI-style questions based on their content.</p>
        </div>
        <nav>
          <a routerLink="/upload">Upload</a>
          <a routerLink="/chat">Chat</a>
        </nav>
      </header>
      <main>
        <router-outlet />
      </main>
    </div>
  `
})
export class AppComponent {}
