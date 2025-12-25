import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DataService } from '../../../services/data';
import { Campo } from '../../../models';

@Component({
  selector: 'app-campos',
  standalone : true,
  imports: [CommonModule, RouterModule],
  templateUrl: './campos.html',
  styleUrl: './campos.css',
})
export class CamposComponent {
  campos: Campo[] = [];
  sedeNombre: string = '';
  loading: boolean = true;

  constructor (
    private route: ActivatedRoute,
    private dataService : DataService
  ) {}

  ngOnInit(): void {
    const sedeId = Number(this.route.snapshot.paramMap.get('sedeId'));
    
    this.dataService.getCamposPorSede(sedeId).subscribe({
      next: (data) => {
        this.campos = data;
        if (data.length > 0) this.sedeNombre = data[0].sede.nombre;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar campos', err);
        this.loading = false;
      }
    });
  }
}


