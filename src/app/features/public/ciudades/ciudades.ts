import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DataService } from '../../../services/data';
import { Ciudad } from '../../../models';

@Component({
  selector: 'app-ciudades',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './ciudades.html',
  styleUrls: ['./ciudades.css']

})
export class CiudadesComponent implements OnInit {
  ciudades: Ciudad[] = [];
  loading: boolean = true;

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.getCiudades().subscribe({
      next: (data) => {
        this.ciudades = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar ciudades', err);
        this.loading = false;
      }
    });
  }
}