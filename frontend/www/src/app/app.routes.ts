import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { ListaProductos } from './features/productos/lista-productos/lista-productos';
import { FormProducto } from './features/productos/form-producto/form-producto';
import { Listacategorias } from './features/categorias/lista-categorias/lista-categorias';
import { FormCategoria } from './features/categorias/form-categoria/form-categoria';

export const routes: Routes = [
    { path: 'login', component: Login },
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'productos', component: ListaProductos },
    { path: 'productos/nuevo', component: FormProducto },
    { path: 'productos/editar/:id', component: FormProducto },
    { path: 'categorias', component: Listacategorias },
    { path: 'categorias/nuevo', component: FormCategoria },
    { path: 'categorias/editar/:id', component: FormCategoria },
];
