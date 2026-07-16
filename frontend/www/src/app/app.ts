import { Component, inject } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { Notificaciones } from './shared/notificaciones/notificaciones';
import { Auth } from './core/services/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive, Notificaciones],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  private auth = inject(Auth);
  private router = inject(Router);

  estaAutenticado(): boolean {
    return this.auth.estaAutenticado();
  }

  cerrarSesion(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
