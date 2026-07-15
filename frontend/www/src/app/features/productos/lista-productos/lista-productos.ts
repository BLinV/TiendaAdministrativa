import { Component, inject, signal, OnInit } from '@angular/core';
import { ProductoService } from '../../../core/services/producto';
import { Producto } from '../../../core/models/producto.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-lista-productos',
  imports: [CurrencyPipe],
  templateUrl: './lista-productos.html',
  styleUrl: './lista-productos.scss'
})
export class ListaProductos implements OnInit {
  private productoService = inject(ProductoService);

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
}
