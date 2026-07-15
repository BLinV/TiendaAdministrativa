import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../services/auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(Auth);
  const token = auth.getToken();

  // Si hay token, clonamos la petición añadiéndole la cabecera Authorization.
  if (token) {
    const reqConToken = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
    return next(reqConToken);
  }

  // Sin token (ej: el propio login), la petición sigue sin tocar.
  return next(req);
};
