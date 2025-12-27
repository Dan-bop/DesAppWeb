import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { DataService } from '../../../services/data';
import { Sede } from '../../../models';

@Component({
  selector: 'app-sedes',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sedes.html',
  styleUrl: './sedes.css',
})
export class SedesComponent implements OnInit {
  sedes: Sede[] = [];
  ciudadNombre: string = '';

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const paramsId = this.route.snapshot.paramMap.get('ciudadId');
    const ciudadId = Number(paramsId);

    if (isNaN(ciudadId) || ciudadId <= 0) {
      this.router.navigate(['/inicio']);
      return;
    }

    this.dataService.getSedesPorCiudad(ciudadId).subscribe({
      next: (data) => {
        this.sedes = data;
        if (data && data.length > 0 && data[0].ciudad) {
          this.ciudadNombre = data[0].ciudad.nombre;
        }
      },
      error: (err) => {
        console.error('Error al cargar Sedes', err);
      }
    });
  }

  // ✅ ESTA ES LA FUNCIÓN QUE FALTABA Y CAUSABA EL ERROR
  manejarErrorImagen(event: any) {
    // Si el link de internet falla, carga esta imagen por defecto
    event.target.src = 'assets/img/default-sede.jpg';
  }
}