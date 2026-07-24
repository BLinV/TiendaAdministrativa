import { CategoriaService } from './../../../core/services/categoria';
import { Component, inject, signal, OnInit } from '@angular/core';
import { ProductoService } from '../../../core/services/producto';
import { Producto } from '../../../core/models/producto.model';
import { Router, RouterLink } from '@angular/router';
import { Categoria } from '../../../core/models/categoria.model';
import { FormsModule } from '@angular/forms';
import { TarjetaProducto } from '../../../shared/tarjeta-producto/tarjeta-producto';
import { NotificacionService } from '../../../core/services/notificacion';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-lista-productos',
  imports: [FormsModule, TarjetaProducto, RouterLink],
  templateUrl: './lista-productos.html',
  styleUrl: './lista-productos.scss'
})
export class ListaProductos implements OnInit {
  private productoService = inject(ProductoService);
  private categoriaService = inject(CategoriaService);
  private router = inject(Router);

  productos = signal<Producto[]>([]);
  cargando = signal(false);

  // Filtros
  filtroCategoria = signal<number | null>(null);
  filtroPrecioMax = signal<number | null>(null);
  filtroNombre = signal<string>('');
  categorias = signal<Categoria[]>([]);

  private notif = inject(NotificacionService);

  ngOnInit(): void {
    this.categoriaService.listar().subscribe(c => this.categorias.set(c));  // para el dropdown
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando.set(true);
    this.productoService.listar(
      this.filtroCategoria() ?? undefined,
      this.filtroPrecioMax() ?? undefined,
      this.filtroNombre() || undefined
    ).subscribe({
      next: (data) => { this.productos.set(data); this.cargando.set(false); },
      error: () => this.cargando.set(false)
    });
  }

  nuevoProducto(): void {
    this.router.navigate(['/productos/nuevo']);
  }

  editar(id: number): void {
    this.router.navigate(['/productos/editar', id]);
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que deseas eliminar este producto?')) return;

    this.productoService.eliminar(id).subscribe({
      next: () => { this.notif.exito('Operación exitosa'); this.cargarProductos(); }, // recargar la lista tras borrar
      error: (err: HttpErrorResponse) => this.notif.errorDesde(err, 'No se pudo eliminar')
    });
  }

  limpiarFiltros(): void {
    this.filtroCategoria.set(null);
    this.filtroPrecioMax.set(null);
    this.filtroNombre.set('');
    this.cargarProductos();
  }
}
