import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ciudad, Sede, Campo, UsuarioResponse } from '../models';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private API_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // --- MÉTODOS DE NAVEGACIÓN ---
  getCiudades(): Observable<Ciudad[]> {
    return this.http.get<Ciudad[]>(`${this.API_URL}/ciudades`);
  }

  getSedesPorCiudad(ciudadId: number): Observable<Sede[]> {
    return this.http.get<Sede[]>(`${this.API_URL}/sedes/ciudad/${ciudadId}`);
  }

  getCamposPorSede(sedeId: number): Observable<Campo[]> {
    return this.http.get<Campo[]>(`${this.API_URL}/campos/sede/${sedeId}`);
  }

  getCampoDetalle(id: number): Observable<Campo> {
    return this.http.get<Campo>(`${this.API_URL}/campos/${id}`);
  }

  // --- MÉTODOS DE RESERVAS ---
  getReservasPorUsuario(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/reservas/usuario/${usuarioId}`);
  }

  
 
  // En DataService.ts

  actualizarPerfil(email: string, request: any): Observable<UsuarioResponse> {
    return this.http.put<UsuarioResponse>(`${this.API_URL}/usuarios/perfil?email=${email}`, request);
  }

  cambiarPassword(email: string, request: any): Observable<any> {
    return this.http.put(`${this.API_URL}/usuarios/cambiar-password?email=${email}`, request);
  }

  eliminarUsuario(email: string): Observable<any> {
    return this.http.delete(`${this.API_URL}/usuarios/perfil?email=${email}`);
  }
}