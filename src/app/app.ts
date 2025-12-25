import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './core/components/navbar/navbar';

@Component({
  selector: 'app-root',
  standalone: true, // 🔽 recomendable para standalone
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css'] // ✅ corregido
})
export class AppComponent {
  protected readonly title = signal('Pacific-Sport');
}
