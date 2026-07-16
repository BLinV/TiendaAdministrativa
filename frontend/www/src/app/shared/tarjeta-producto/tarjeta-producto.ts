import { Component, input } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { Producto } from '../../core/models/producto.model';

@Component({
  selector: 'app-tarjeta-producto',
  imports: [CurrencyPipe],
  templateUrl: './tarjeta-producto.html',
  styleUrl: './tarjeta-producto.scss'
})
export class TarjetaProducto {
  producto = input.required<Producto>();   // ← recibe el producto de fuera
}
