import { Component, OnInit, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../services/auth';
import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  userName = '';
  isAdmin = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object
  ) {}

  ngOnInit(): void {
    this.checkUserStatus();
  }

  checkUserStatus() {
    if (!isPlatformBrowser(this.platformId)) return; // 👈 SOLO navegador

    const userJson = localStorage.getItem('currentUser');
    if (userJson) {
      const user = JSON.parse(userJson);
      this.isLoggedIn = true;
      this.userName = user.nombre;
      this.isAdmin = user.rol === 'ADMIN';
    } else {
      this.isLoggedIn = false;
      this.userName = '';
      this.isAdmin = false;
    }
  }

  onLogout() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
