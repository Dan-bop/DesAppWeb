import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../services/reserva';
import { ReservaRequest, UsuarioResponse } from '../../../models';

@Component({
  selector: 'app-crear-reserva',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './crear-reserva.html',
  styleUrl: './crear-reserva.css',
})
export class CrearReservaComponent implements OnInit {
  preReserva: any;
  usuarioLogueado: UsuarioResponse | null = null;
  
  datosInvitado = {
    nombre: '',
    email: '',
    telefono: ''
  };
  
  loading = false;

  constructor(
    private reservaService: ReservaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Obtener la elección que viene de Campo Detalle
    const stored = localStorage.getItem('preReserva');
    if (!stored) {
      this.router.navigate(['/campos']); // Si no hay nada, regresa al catálogo
      return;
    }
    this.preReserva = JSON.parse(stored);

    // 2. Autocompletar si el usuario tiene sesión iniciada
    const userStored = localStorage.getItem('currentUser');
    if (userStored) {
      this.usuarioLogueado = JSON.parse(userStored);
      this.datosInvitado.nombre = this.usuarioLogueado?.nombre || '';
      this.datosInvitado.email = this.usuarioLogueado?.email || '';
    }
  }

  confirmarReserva() {
    this.loading = true;

    // Ya no hacemos split de horaSeleccionada, usamos los datos limpios de preReserva
    const request: ReservaRequest = {
      campoId: this.preReserva.campo.id,
      fecha: this.preReserva.fecha,
      horaInicio: this.preReserva.horaInicio,
      horaFin: this.preReserva.horaFin,
      nombreInvitado: this.datosInvitado.nombre,
      emailInvitado: this.datosInvitado.email,
      telefonoInvitado: this.datosInvitado.telefono
    };

    this.reservaService.crearReserva(request).subscribe({
      next: (reservaGuardada) => {
        localStorage.removeItem('preReserva'); // Limpiar después de éxito
        alert('¡Reserva creada con éxito! Ahora puedes proceder al pago.');
        this.router.navigate(['/pago', reservaGuardada.id]);
      },
      error: (err) => {
        console.error(err);
        alert('Error al crear reserva: ' + (err.error?.message || 'El horario ya no está disponible'));
        this.loading = false;
      }
    });
  }
}