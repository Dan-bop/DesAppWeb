import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ReservaService } from '../../../services/reserva';
import { ReservaRequest } from '../../../models';

@Component({
  selector: 'app-crear-reserva',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './crear-reserva.html',
  styleUrl: './crear-reserva.css',
})
export class CrearReservaComponent implements OnInit {
  preReserva : any;
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

  horariosDisponibles: string[] = [];
horarioSeleccionado: string = '';

 ngOnInit(): void {
  const stored = localStorage.getItem('preReserva');
  if (!stored) {
    this.router.navigate(['/ciudades']);
    return;
  }
  this.preReserva = JSON.parse(stored);

  // 🔽 Llamada al backend para traer horarios disponibles
  this.reservaService.getDisponibilidad(this.preReserva.campo.id, this.preReserva.fecha)
    .subscribe(horarios => this.horariosDisponibles = horarios);
}


  confirmarReserva() {
  this.loading = true;

  const [horaInicio, horaFin] = this.horarioSeleccionado.split(' - ');

  const request: ReservaRequest = {
    campoId: this.preReserva.campo.id,
    fecha: this.preReserva.fecha,
    horaInicio: horaInicio,
    horaFin: horaFin,
    nombreInvitado: this.datosInvitado.nombre,
    emailInvitado: this.datosInvitado.email,
    telefonoInvitado: this.datosInvitado.telefono
  };

  this.reservaService.crearReserva(request).subscribe({
    next: (reservaGuardada) => {
      alert('¡Reserva creada! Ahora procede al pago.');
      this.router.navigate(['/pago', reservaGuardada.id]);
    },
    error: (err) => {
      alert('Error al crear reserva: ' + err.error);
      this.loading = false;
    }
  });

}
}