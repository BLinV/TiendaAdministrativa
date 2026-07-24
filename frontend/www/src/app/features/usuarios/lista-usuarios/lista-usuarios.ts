import { Component, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from '../../../core/services/usuario';
import { Usuario } from '../../../core/models/usuario.model';
import { NotificacionService } from '../../../core/services/notificacion';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-lista-usuarios',
  imports: [],
  templateUrl: './lista-usuarios.html',
  styleUrl: './lista-usuarios.scss'
})
export class ListaUsuarios implements OnInit {
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private notif = inject(NotificacionService);

  usuarios = signal<Usuario[]>([]);
  cargando = signal(false);

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.cargando.set(true);
    this.usuarioService.listar().subscribe({
      next: (data) => { this.usuarios.set(data); this.cargando.set(false); },
      error: () => this.cargando.set(false)
    });
  }

  nuevoUsuario(): void {
    this.router.navigate(['/usuarios/nuevo']);
  }

  editar(id: number): void {
    this.router.navigate(['/usuarios/editar', id]);
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que deseas eliminar este usuario?')) return;

    this.usuarioService.eliminar(id).subscribe({
      next: () => { this.notif.exito('Usuario eliminado'); this.cargarUsuarios(); },
      error: (err: HttpErrorResponse) => this.notif.errorDesde(err, 'No se pudo eliminar')
    });
  }
}
