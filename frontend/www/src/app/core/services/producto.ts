import { environment } from './../../../environments/environments';
import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/productos`;

  listar(idCategoria?: number, precioMax?: number, nombre?: string): Observable<Producto[]> {
    let params = new HttpParams();
    if (idCategoria != null) params = params.set('idCategoria', idCategoria);
    if (precioMax != null)   params = params.set('precioMax', precioMax);
    if (nombre)              params = params.set('nombre', nombre);

    return this.http.get<Producto[]>(this.apiUrl, { params });
  }
}
