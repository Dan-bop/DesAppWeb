import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { RegistroRequest, LoginRequest, UsuarioResponse } from '../models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API_URL = 'http://localhost:8080/usuarios';

  constructor(private http: HttpClient) { }

  registrar(usuario: RegistroRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.API_URL}/registro`, usuario);
  }

  login(credenciales: LoginRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.API_URL}/login`, credenciales).pipe(
      tap((user: UsuarioResponse) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
      })
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('currentUser') !== null;
  }

 getUserRole(): string {
  const user = JSON.parse(localStorage.getItem('currentUser') || '{}');
  return user.rol || 'USER';
}

}
