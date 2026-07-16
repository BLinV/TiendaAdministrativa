import { Component, inject } from '@angular/core';
import { NotificacionService } from '../../core/services/notificacion';

@Component({
  selector: 'app-notificaciones',
  imports: [],
  templateUrl: './notificaciones.html',
  styleUrl: './notificaciones.scss'
})
export class Notificaciones {
  private servicio = inject(NotificacionService);
  notificacion = this.servicio.notificacion;   // expone el signal al HTML
}
