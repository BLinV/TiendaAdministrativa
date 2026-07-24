import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { ListaProductos } from './features/productos/lista-productos/lista-productos';
import { FormProducto } from './features/productos/form-producto/form-producto';
import { ListaCategorias } from './features/categorias/lista-categorias/lista-categorias';
import { FormCategoria } from './features/categorias/form-categoria/form-categoria';
import { DetalleProducto } from './features/productos/detalle-producto/detalle-producto';
import { ListaUsuarios } from './features/usuarios/lista-usuarios/lista-usuarios';
import { FormUsuario } from './features/usuarios/form-usuario/form-usuario';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { path: 'login', component: Login },   // ← SIN guard (público)
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'productos', component: ListaProductos, canActivate: [authGuard] },
  { path: 'productos/nuevo', component: FormProducto, canActivate: [authGuard] },
  { path: 'productos/editar/:id', component: FormProducto, canActivate: [authGuard] },
  { path: 'productos/:id', component: DetalleProducto, canActivate: [authGuard] },

  { path: 'categorias', component: ListaCategorias, canActivate: [authGuard] },
  { path: 'categorias/nuevo', component: FormCategoria, canActivate: [authGuard] },
  { path: 'categorias/editar/:id', component: FormCategoria, canActivate: [authGuard] },

  { path: 'usuarios', component: ListaUsuarios, canActivate: [authGuard] },
  { path: 'usuarios/nuevo', component: FormUsuario, canActivate: [authGuard] },
  { path: 'usuarios/editar/:id', component: FormUsuario, canActivate: [authGuard] },
];
