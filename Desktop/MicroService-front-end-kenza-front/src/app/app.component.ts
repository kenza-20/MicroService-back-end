import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent {
  currentRole = localStorage.getItem('user_role') || 'HR';

  constructor(private router: Router) {}

  switchRole(): void {
    // Toggle rôle
    this.currentRole = this.currentRole === 'HR' ? 'USER' : 'HR';
    localStorage.setItem('user_role', this.currentRole);

    // Redirection vers le wrapper pour décider où aller
    this.router.navigateByUrl('/admin/job-offers').then(() => {
      window.location.reload(); // 🔁 recharge l'app pour que le wrapper redirige selon le nouveau rôle
    });
  }
}
