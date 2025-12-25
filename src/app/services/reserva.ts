import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reserva, ReservaRequest } from '../models';

@Injectable({
  providedIn: 'root',
})
export class ReservaService {
  private API_URL = 'http://localhost:8080/reservas';

  constructor(private http: HttpClient) {}

  // Obtiene las horas libres (ej: ["08:00", "09:00", "15:00"])
  getDisponibilidad(campoId: number, fecha: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.API_URL}/disponibilidad?campoId=${campoId}&fecha=${fecha}`);
  }

  //Reserva Inicial
  crearReserva(reserva: ReservaRequest, userId?: number): Observable<Reserva> {
  const url = userId ? `${this.API_URL}?userId=${userId}` : this.API_URL;
    return this.http.post<Reserva>(url, reserva);
  }
}
  
