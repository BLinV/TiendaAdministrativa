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

  private expirado(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 < Date.now();
    } catch {
      return true;                    // token ilegible → inválido
    }
  }

  estaAutenticado(): boolean {
    const token = this.getToken();
    if (!token || this.expirado(token)) {
      this.logout();                  // limpia el token muerto de paso
      return false;
    }
    return true;
  }
}
