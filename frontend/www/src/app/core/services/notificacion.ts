import { Injectable, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

export interface Notificacion {
  tipo: 'exito' | 'error';
  mensaje: string;
}

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  // el signal que el componente de alertas "escucha"
  notificacion = signal<Notificacion | null>(null);

  exito(mensaje: string): void {
    this.mostrar('exito', mensaje);
  }

  error(mensaje: string): void {
    this.mostrar('error', mensaje);
  }

  private mostrar(tipo: 'exito' | 'error', mensaje: string): void {
    this.notificacion.set({ tipo, mensaje });
    // se oculta sola tras 3 segundos
    setTimeout(() => this.notificacion.set(null), 3000);
  }

  errorDesde(err: HttpErrorResponse, porDefecto: string): void {
    this.error(err.error?.mensaje ?? porDefecto);
  }
}
