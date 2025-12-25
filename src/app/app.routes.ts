import { Routes } from '@angular/router';

export const routes: Routes = [
  // MÓDULO PÚBLICO
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', loadComponent: () => import('./features/public/inicio/inicio').then(m => m.Inicio) },
  { path: 'ciudades', loadComponent: () => import('./features/public/ciudades/ciudades').then(m => m.CiudadesComponent) },
  { path: 'sedes/:ciudadId', loadComponent: () => import('./features/public/sedes/sedes').then(m => m.SedesComponent) },
  { path: 'campos/:sedeId', loadComponent: () => import('./features/public/campos/campos').then(m => m.CamposComponent) },
  { path: 'campo-detalle/:id', loadComponent: () => import('./features/public/campo-detalle/campo-detalle').then(m => m.CampoDetalleComponent) },
  { path: 'crear-reserva', loadComponent: () => import('./features/public/crear-reserva/crear-reserva').then(m => m.CrearReservaComponent) },
  { path: 'pago/:reservaId', loadComponent: () => import('./features/public/pago-simulacion/pago-simulacion').then(m => m.PagoSimulacionComponent) },

  // MÓDULO AUTH
  { path: 'login', loadComponent: () => import('./features/auth/login/login').then(m => m.LoginComponent) },
  { path: 'registro', loadComponent: () => import('./features/auth/registro/registro').then(m => m.RegistroComponent) },

  // MÓDULO ADMIN (CRUD)
  { path: 'admin/dashboard', loadComponent: () => import('./features/admin/dashboard/dashboard').then(m => m.Dashboard) },
  { path: 'admin/canchas', loadComponent: () => import('./features/admin/gestion-campos/gestion-campos').then(m => m.GestionCampos) },];