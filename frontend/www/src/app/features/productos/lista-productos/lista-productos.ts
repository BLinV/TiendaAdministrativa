import { Component, inject, signal, OnInit } from '@angular/core';
import { ProductoService } from '../../../core/services/producto';
import { Producto } from '../../../core/models/producto.model';
import { CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-productos',
  imports: [CurrencyPipe],
  templateUrl: './lista-productos.html',
  styleUrl: './lista-productos.scss'
})
export class ListaProductos implements OnInit {
  private productoService = inject(ProductoService);
  private router = inject(Router);

  productos = signal<Producto[]>([]);
  cargando = signal(false);

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando.set(true);
    this.productoService.listar().subscribe({
      next: (data) => {
        this.productos.set(data);
        this.cargando.set(false);
      },
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
      next: () => this.cargarProductos(),   // recargar la lista tras borrar
      error: () => alert('No se pudo eliminar')
    });
  }
}
