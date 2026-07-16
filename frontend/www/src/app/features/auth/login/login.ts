import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../../core/services/auth';
import { NotificacionService } from '../../../core/services/notificacion';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  private fb = inject(FormBuilder);
  private auth = inject(Auth);
  private router = inject(Router);

  error = signal<string | null>(null);

  private notif = inject(NotificacionService);

  form = this.fb.group({
    usuario: ['', Validators.required],
    clave: ['', Validators.required]
  });

  onSubmit(): void {
    if (this.form.invalid) return;

    const { usuario, clave } = this.form.value;
    this.auth.login(usuario!, clave!).subscribe({
      next: () => { this.notif.exito('Bienvenido'); this.router.navigate(['/productos']); },
      error: () => this.error.set('Credenciales inválidas')
    });
  }
}
