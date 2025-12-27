import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DataService } from '../../../services/data'; // Asegúrate de tener este servicio
import { Ciudad } from '../../../models';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inicio.html',
  styleUrl: './inicio.css'
})
export class InicioComponent implements OnInit {
  ciudades: Ciudad[] = [];

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    // Traemos las ciudades reales de la BD (ej: Trujillo, Chimbote, Lima)
    this.dataService.getCiudades().subscribe({
      next: (data) => this.ciudades = data,
      error: (err) => console.error('Error al cargar ciudades', err)
    });
  }
}