import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PagoService } from '../../../services/pago';
import { PagoRequest } from '../../../models';

@Component({
  selector: 'app-pago-simulacion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pago-simulacion.html',
  styleUrl: './pago-simulacion.css',
})
export class PagoSimulacionComponent implements OnInit {
  reservaId!: number;
  metodoSeleccionado: string = 'TARJETA';
  loading = false;
  
  // Datos para el request
  pago: PagoRequest = {
    monto: 0, // Se llenará con el precio de la reserva
    metodoPago: 'TARJETA',
    titular: '',
    numeroTarjeta: '',
    vencimiento: '',
    cvv: ''
  };

  constructor(
    private route: ActivatedRoute,
    private pagoService: PagoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.reservaId = Number(this.route.snapshot.paramMap.get('reservaId'));
    // Recuperamos el monto de la pre-reserva guardada en localStorage
    const preReserva = JSON.parse(localStorage.getItem('preReserva') || '{}');
    this.pago.monto = preReserva.campo?.precioHora || 0;
  }

  procesarPago() {
    this.loading = true;
    this.pago.metodoPago = this.metodoSeleccionado;

    this.pagoService.registrarPago(this.reservaId, this.pago).subscribe({
      next: (res) => {
        alert('¡Pago exitoso! Revisa tu correo electrónico para la confirmación.');
        localStorage.removeItem('preReserva'); // Limpiamos el carrito
        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        alert('Error al procesar el pago. Intenta de nuevo.');
        this.loading = false;
      }
    });
  }
}
