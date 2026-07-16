import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { Auth } from '../services/auth';

export const authGuard: CanActivateFn = (route, state) => {
  const auth = inject(Auth);
  const router = inject(Router);

  if (auth.estaAutenticado()) {
    return true;                          // hay token → deja pasar
  }

  router.navigate(['/login']);           // no hay token → redirige al login
  return false;                          // y bloquea la ruta
};
