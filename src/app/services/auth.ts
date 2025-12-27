import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { RegistroRequest, LoginRequest, UsuarioResponse } from '../models';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API_URL = 'http://localhost:8080/usuarios';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  registrar(usuario: RegistroRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.API_URL}/registro`, usuario);
  }

  login(credenciales: LoginRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(`${this.API_URL}/login`, credenciales).pipe(
      tap((user: UsuarioResponse) => {
        if (this.isBrowser()) {
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      })
    );
  }

  logout() {
    if (this.isBrowser()) {
      localStorage.removeItem('currentUser');
    }
  }

  isLoggedIn(): boolean {
    if (!this.isBrowser()) return false;
    return localStorage.getItem('currentUser') !== null;
  }

  getUserRole(): string {
    if (!this.isBrowser()) return 'USER';

    const user = JSON.parse(localStorage.getItem('currentUser') || '{}');
    return user.rol || 'USER';
  }
}
