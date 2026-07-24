import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../../../core/services/usuario';
import { NotificacionService } from '../../../core/services/notificacion';

@Component({
  selector: 'app-form-usuario',
  imports: [ReactiveFormsModule],
  templateUrl: './form-usuario.html',
  styleUrl: './form-usuario.scss'
})
export class FormUsuario implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private notif = inject(NotificacionService);

  editando = signal(false);
  private idEditar: number | null = null;

  form = this.fb.group({
    nombre: ['', [Validators.required, Validators.maxLength(100)]],
    usuario: ['', [Validators.required, Validators.maxLength(30)]],
    clave: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(100)]]
  });

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando.set(true);
      this.idEditar = Number(id);
      // El backend NO devuelve la clave (por seguridad), así que solo se
      // rellenan nombre y usuario. La clave debe reescribirse al editar.
      this.usuarioService.obtenerPorId(this.idEditar).subscribe(u => {
        this.form.patchValue({ nombre: u.nombre, usuario: u.usuario });
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const datos = this.form.value as any;

    const peticion = this.editando()
      ? this.usuarioService.actualizar(this.idEditar!, datos)
      : this.usuarioService.crear(datos);

    peticion.subscribe({
      next: () => { this.notif.exito('Operación exitosa'); this.router.navigate(['/usuarios']); },
      error: () => this.notif.error('Error al guardar')
    });
  }

  cancelar(): void {
    this.router.navigate(['/usuarios']);
  }
}
