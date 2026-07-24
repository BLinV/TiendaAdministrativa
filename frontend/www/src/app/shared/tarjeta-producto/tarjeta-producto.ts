import { Component, input } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { Producto } from '../../core/models/producto.model';

@Component({
  selector: 'app-tarjeta-producto',
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './tarjeta-producto.html',
  styleUrl: './tarjeta-producto.scss'
})
export class TarjetaProducto {
  producto = input.required<Producto>();   // ← recibe el producto de fuera
  detalle = input(false);
}
