import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Notificaciones } from './shared/notificaciones/notificaciones';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Notificaciones],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  
}
