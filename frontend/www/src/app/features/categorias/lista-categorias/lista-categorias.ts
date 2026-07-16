import { CategoriaService } from './../../../core/services/categoria';
import { Component, inject, signal, OnInit } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';
import { Categoria } from '../../../core/models/categoria.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-categorias',
  imports: [FormsModule],
  templateUrl: './lista-categorias.html',
  styleUrl: './lista-categorias.scss'
})
export class Listacategorias implements OnInit {
  private categoriaService = inject(CategoriaService);
  private router = inject(Router);

  categorias = signal<Categoria[]>([]);
  cargando = signal(false);

  ngOnInit(): void {
    this.categoriaService.listar().subscribe(c => this.categorias.set(c));  // para el dropdown
    this.cargarcategorias();
  }

  cargarcategorias(): void {
    this.cargando.set(true);
    this.categoriaService.listar().subscribe({
      next: (data) => { this.categorias.set(data); this.cargando.set(false); },
      error: () => this.cargando.set(false)
    });
  }

  nuevaCategoria(): void {
    this.router.navigate(['/categorias/nuevo']);
  }

  administrarProductos(): void {
    this.router.navigate(['/productos']);
  }

  editar(id: number): void {
    this.router.navigate(['/categorias/editar', id]);
  }

  eliminar(id: number): void {
    if (!confirm('¿Seguro que deseas eliminar esta categoria?')) return;

    this.categoriaService.eliminar(id).subscribe({
      next: () => this.cargarcategorias(),   // recargar la lista tras borrar
      error: () => alert('No se pudo eliminar')
    });
  }
}
