import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago, PagoRequest } from '../models';

@Injectable({
  providedIn: 'root',
})
export class PagoService {
  private API_URL='htpp://localhost:8080/pagos';

  constructor (private http:HttpClient) {}
  //Envia el pago
  registrarPago(reservaId: number, pago: PagoRequest): Observable<Pago> {
  return this.http.post<Pago>(`${this.API_URL}/registrar/${reservaId}`, pago);
}

obtenerPagoPorReserva(reservaId: number): Observable<Pago> {
  return this.http.get<Pago>(`${this.API_URL}/reserva/${reservaId}`);
}

listarPagosPorUsuario(usuarioId: number): Observable<Pago[]> {
  return this.http.get<Pago[]>(`${this.API_URL}/usuario/${usuarioId}`);
}
}
