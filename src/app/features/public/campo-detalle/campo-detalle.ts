import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../../services/data';
import { ReservaService } from '../../../services/reserva';
import { Campo } from '../../../models';

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
  horasLibres: string[] = []; 
  horaSeleccionada: string = '';
  duracionSeleccionada: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dataService: DataService,
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.dataService.getCampoDetalle(id).subscribe((data) => {
      this.campo = data;
      this.consultarHoras();
    });
  }

  consultarHoras() {
    if (!this.campo) return;
    this.reservaService
      .getDisponibilidad(this.campo.id, this.fechaSeleccionada)
      .subscribe((horas) => {
        this.horasLibres = horas;
        this.horaSeleccionada = ''; 
      });
  }

  public calcularHoraFin(inicio: string, horas: any): string {
    if (!inicio) return '';
    // Extraemos solo la primera hora si viene un rango (ej: "08:00")
    const horaLimpia = inicio.split(' ')[0];
    const [h, m] = horaLimpia.split(':').map(Number);
    const horaFin = h + Number(horas); 
    return `${horaFin.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;
  }

  // NUEVO: Método público para que el HTML valide el rango siguiente
  public calcularSiguienteRango(rango: string): string {
    if (!rango || !rango.includes(' - ')) return '';
    const finPrimerBloque = rango.split(' - ')[1]; 
    const finSegundoBloque = this.calcularHoraFin(finPrimerBloque, 1);
    return `${finPrimerBloque} - ${finSegundoBloque}`;
  }

  private esBloqueValido(): boolean {
    if (this.duracionSeleccionada == 1) return true;
    const rangoBuscado = this.calcularSiguienteRango(this.horaSeleccionada);
    const estaDisponible = this.horasLibres.includes(rangoBuscado);
    
    if (!estaDisponible) {
      alert(`No se puede reservar 2 horas porque el horario siguiente está ocupado.`);
    }
    return estaDisponible;
  }

  irAReservar() {
    if (!this.horaSeleccionada) return alert('Por favor, selecciona una hora de inicio');
    
    if (this.duracionSeleccionada > 2) {
      this.duracionSeleccionada = 2;
    }

    if (!this.esBloqueValido()) return;

    const horaInicioReal = this.horaSeleccionada.split(' ')[0];

    const preReserva = {
      campo: this.campo,
      fecha: this.fechaSeleccionada,
      horaInicio: horaInicioReal,
      duracion: Number(this.duracionSeleccionada),
      horaFin: this.calcularHoraFin(horaInicioReal, this.duracionSeleccionada)
    };

    localStorage.setItem('preReserva', JSON.stringify(preReserva));
    this.router.navigate(['/crear-reserva']);
  }
}