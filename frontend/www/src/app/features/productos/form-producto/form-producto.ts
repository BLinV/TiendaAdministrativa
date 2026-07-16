import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../../core/services/producto';
import { CategoriaService } from '../../../core/services/categoria';
import { Categoria } from '../../../core/models/categoria.model';
import { NotificacionService } from '../../../core/services/notificacion';

@Component({
  selector: 'app-form-producto',
  imports: [ReactiveFormsModule],
  templateUrl: './form-producto.html',
  styleUrl: './form-producto.scss'
})
export class FormProducto implements OnInit {
  private fb = inject(FormBuilder);
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  categorias = signal<Categoria[]>([]);
  editando = signal(false);
  private idEditar: number | null = null;

  private notif = inject(NotificacionService);

  form = this.fb.group({
    nombre: ['', [Validators.required, Validators.maxLength(255)]],
    descripcion: ['', Validators.maxLength(255)],
    precio: [0, [Validators.required, Validators.min(0.01)]],
    stock: [0, [Validators.required, Validators.min(0)]],
    idCategoria: [null as number | null, Validators.required]
  });

  ngOnInit(): void {
    // cargar categorías para el dropdown
    this.categoriaService.listar().subscribe(cats => this.categorias.set(cats));

    // ¿venimos a editar? la URL trae un id
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editando.set(true);
      this.idEditar = Number(id);
      this.productoService.obtenerPorId(this.idEditar).subscribe(p => {
        this.form.patchValue(p);   // rellena el form con los datos existentes
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;
    const datos = this.form.value as any;

    const peticion = this.editando()
      ? this.productoService.actualizar(this.idEditar!, datos)
      : this.productoService.crear(datos);

    peticion.subscribe({
      next: () => { this.notif.exito('Operación exitosa'); this.router.navigate(['/productos']); },
      error: () => this.notif.error('Error al guardar el producto')
    });
  }

  cancelar(): void {
    this.router.navigate(['/productos']);
  }
}
