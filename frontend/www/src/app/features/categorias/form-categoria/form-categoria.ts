import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CategoriaService } from '../../../core/services/categoria';
import { Categoria } from '../../../core/models/categoria.model';
import { NotificacionService } from '../../../core/services/notificacion';

@Component({
  selector: 'app-form-categoria',
  imports: [ReactiveFormsModule],
  templateUrl: './form-categoria.html',
  styleUrl: './form-categoria.scss'
})
export class FormCategoria implements OnInit {
  private fb = inject(FormBuilder);
  private categoriaService = inject(CategoriaService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  editando = signal(false);
  private idEditar: number | null = null;

  private notif = inject(NotificacionService);

  form = this.fb.group({
    nombre: ['', [Validators.required, Validators.maxLength(255)]],
    descripcion: ['', Validators.maxLength(255)]
  });

  ngOnInit(): void {
    // ¿venimos a editar? la URL trae un id
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando.set(true);
      this.idEditar = Number(id);
      this.categoriaService.obtenerPorId(this.idEditar).subscribe(p => {
        this.form.patchValue(p);   // rellena el form con los datos existentes
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const datos = this.form.value as any;

    const peticion = this.editando()
      ? this.categoriaService.actualizar(this.idEditar!, datos)
      : this.categoriaService.crear(datos);

    peticion.subscribe({
      next: () => { this.notif.exito('Operación exitosa'); this.router.navigate(['/categorias']); },
      error: () => this.notif.error('Error al guardar')
    });
  }

  cancelar(): void {
    this.router.navigate(['/categorias']);
  }
}
