import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private http = inject(HttpClient); //Inyectar dependencias
  private readonly TOKEN_KEY = 'token';

  login(usuario: string, clave: string): Observable<{ token: string }> {
    return this.http
          .post<{ token: string }>(`${environment.apiUrl}/auth/login`, { usuario, clave })
          .pipe(tap(res => localStorage.setItem(this.TOKEN_KEY, res.token)));
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  estaAutenticado(): boolean {
    return this.getToken() !== null;
  }
}
