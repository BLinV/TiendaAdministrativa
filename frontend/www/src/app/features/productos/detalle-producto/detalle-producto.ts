import { Component, inject, signal, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from '../../../core/services/producto';
import { Producto } from '../../../core/models/producto.model';
import { TarjetaProducto } from '../../../shared/tarjeta-producto/tarjeta-producto';  // ← paso 1

@Component({
  selector: 'app-detalle-producto',
  imports: [TarjetaProducto],   // ← paso 2
  templateUrl: './detalle-producto.html',
  styleUrl: './detalle-producto.scss'
})
export class DetalleProducto implements OnInit {
  private route = inject(ActivatedRoute);
  private productoService = inject(ProductoService);
  private router = inject(Router);

  producto = signal<Producto | null>(null);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productoService.obtenerPorId(id).subscribe(p => this.producto.set(p));
  }

  volver(): void {
    this.router.navigate(['/productos']);
  }
}
