import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { AuthService } from '../../../services/auth'; 
import { DataService } from '../../../services/data'; 
import { UsuarioResponse } from '../../../models';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css'
})
export class PerfilComponent implements OnInit {
  usuario: UsuarioResponse | null = null;
  reservas: any[] = []; 

  constructor(
    private authService: AuthService,
    private dataService: DataService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const storedUser = localStorage.getItem('currentUser');
      if (storedUser) {
        this.usuario = JSON.parse(storedUser);
        this.cargarMisReservas();
      }
    }
  }

  cargarMisReservas(): void {
    if (this.usuario?.id) {
      this.dataService.getReservasPorUsuario(this.usuario.id).subscribe({
        next: (data) => this.reservas = data,
        error: (err) => console.error('Error al obtener reservas:', err)
      });
    }
  }

  editarDatos(): void {
    if (!isPlatformBrowser(this.platformId) || !this.usuario?.email) return;

    const nuevoNombre = prompt("Ingresa tu nuevo nombre:", this.usuario.nombre || '');
    if (nuevoNombre && nuevoNombre !== this.usuario.nombre) {
      const updateRequest = { nombre: nuevoNombre };

      // Pasamos el email como primer parámetro
      this.dataService.actualizarPerfil(this.usuario.email, updateRequest).subscribe({
        next: (usuarioActualizado) => {
          alert('¡Datos actualizados!');
          localStorage.setItem('currentUser', JSON.stringify(usuarioActualizado));
          this.usuario = usuarioActualizado;
        },
        error: (err) => alert('Error al actualizar')
      });
    }
  }

  eliminarCuenta(): void {
    if (!isPlatformBrowser(this.platformId) || !this.usuario?.email) return;

    if (confirm('¿Estás SEGURO de eliminar tu cuenta?')) {
      // Pasamos el email para que el Backend sepa a quién borrar
      this.dataService.eliminarUsuario(this.usuario.email).subscribe({
        next: () => {
          alert('Cuenta eliminada.');
          this.logout();
        },
        error: (err) => alert('Error al eliminar cuenta')
      });
    }
  }

  cambiarPass(): void {
    if (!isPlatformBrowser(this.platformId) || !this.usuario?.email) return;

    const actual = prompt("Ingresa tu contraseña actual:");
    const nueva = prompt("Ingresa tu nueva contraseña:");
    
    if (actual && nueva) {
      const request = { 
        passwordActual: actual, 
        nuevaPassword: nueva 
      };
      
      // Pasamos el email al servicio
      this.dataService.cambiarPassword(this.usuario.email, request).subscribe({
        next: () => alert('Contraseña actualizada'),
        error: (err) => alert('Error: La contraseña actual es incorrecta')
      });
    }
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.authService.logout();
      window.location.href = '/inicio';
    }
  }
}