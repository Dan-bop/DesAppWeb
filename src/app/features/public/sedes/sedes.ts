import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DataService } from '../../../services/data';
import { Sede } from '../../../models';

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
    private dataService : DataService
  ) {}
  ngOnInit(): void {
    // OBTENEMOS ID DE LA CIUDAD
    const ciudadId = Number(this.route.snapshot.paramMap.get('ciudadId'));

    this.dataService.getSedesPorCiudad(ciudadId).subscribe({
      next: (data) => {
        this.sedes = data;
        if (data.length > 0) this.ciudadNombre = data[0].ciudad.nombre;
      },
      error : (err) => console.error('Error al cargar Sedes', err)
    });
  }

}
