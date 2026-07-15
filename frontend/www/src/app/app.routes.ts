import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { ListaProductos } from './features/productos/lista-productos/lista-productos';

export const routes: Routes = [
    { path: 'login', component: Login },
    { path: '', redirectTo: 'login', pathMatch: 'full'},
    { path: 'productos', component: ListaProductos },
];
