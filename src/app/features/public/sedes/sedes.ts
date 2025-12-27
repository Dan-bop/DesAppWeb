import { ChangeDetectorRef, Component, OnInit, ɵinjectChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, } from '@angular/router';
import { DataService } from '../../../services/data';
import { Sede } from '../../../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sedes',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './sedes.html',
  styleUrl: './sedes.css',
})
export class SedesComponent implements OnInit {
  sedes : Sede [] = [];
  ciudadNombre : string= '';

  constructor (
    private route : ActivatedRoute,
    private dataService : DataService,
    private router : Router,
    private cdr: ChangeDetectorRef
  ) {}
ngOnInit(): void {
  const paramsId = this.route.snapshot.paramMap.get('ciudadId');
  const ciudadId = Number(paramsId);

  // Validación: Solo procedemos si el ID es un número válido
  if (isNaN(ciudadId) || ciudadId <= 0) {
    console.warn('ID de ciudad no válido');
    this.router.navigate(['/inicio']); // Redirige al inicio si el ID es basura
    return;
  }

  this.dataService.getSedesPorCiudad(ciudadId).subscribe({
    next: (data) => {
      this.sedes = data;
      // Usamos el operador de encadenamiento opcional (?) para evitar errores de null
      if (data && data.length > 0 && data[0].ciudad) {
        this.ciudadNombre = data[0].ciudad.nombre;
      }
    },
    error: (err) => {
      console.error('Error al cargar Sedes', err);
      // Aquí podrías mostrar un mensaje al usuario o una alerta
    }
  });
}
}
