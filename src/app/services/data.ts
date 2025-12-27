import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ciudad,Sede,Campo, ReservaRequest, PagoRequest } from '../models';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private API_URL ='http://localhost:8080';

  constructor(private http: HttpClient) {}

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
  // Obtener reservas por el ID del usuario
getReservasPorUsuario(usuarioId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.API_URL}/reservas/usuario/${usuarioId}`);
}

// Eliminar usuario de la base de datos
eliminarUsuario(usuarioId: number): Observable<any> {
  return this.http.delete(`${this.API_URL}/usuarios/${usuarioId}`);
}

// Opcional: Cambiar contraseña
cambiarPassword(usuarioId: number, nuevaPass: string): Observable<any> {
  return this.http.put(`${this.API_URL}/usuarios/${usuarioId}/password`, { password: nuevaPass });
}
  
}

  

