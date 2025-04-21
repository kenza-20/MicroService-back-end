import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
})
export class AdminComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  // ✅ Cette méthode retourne true si on est sur la page /admin/dashboard
  isDashboardRoute(): boolean {
    return this.router.url.includes('/dashboard');
  }
}
