import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../../services/data';
import { ReservaService } from '../../../services/reserva';
import { Campo, ReservaRequest } from '../../../models';

@Component({
  selector: 'app-campo-detalle',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './campo-detalle.html',
  styleUrl: './campo-detalle.css',
})
export class CampoDetalleComponent implements OnInit {
  campo?: Campo;
  fechaSeleccionada: string = new Date().toISOString().split('T')[0];
  horasLibres : string[] = [];
  horaSeleccionada: string= '';

  constructor (
    private route: ActivatedRoute,
    private router: Router,
    private DataService: DataService,
    private reservaService: ReservaService
  ) {}
   ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.DataService.getCampoDetalle(id).subscribe(data => {
      this.campo = data;
      this.consultarHoras(); // Consultar horas para la fecha de hoy
    });
  }

  consultarHoras() {
    if (!this.campo) return;
    this.reservaService.getDisponibilidad(this.campo.id, this.fechaSeleccionada)
      .subscribe(horas => this.horasLibres = horas);
  }

  irAReservar() {
    if (!this.horaSeleccionada) return alert('Selecciona una hora');
    
    // Guardamos temporalmente en un objeto para pasarlo a la siguiente vista
    const preReserva = {
      campo: this.campo,
      fecha: this.fechaSeleccionada,
      horaInicio: this.horaSeleccionada
    };
    
    localStorage.setItem('preReserva', JSON.stringify(preReserva));
    this.router.navigate(['/crear-reserva']);
  }
}


