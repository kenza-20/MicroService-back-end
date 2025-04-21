import { Component, OnInit } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
})
export class SidebarComponent implements OnInit {
  collapseShow = "hidden";
  userRole: string = "";

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.getUserRole();
  }

  toggleCollapseShow(classes: string) {
    this.collapseShow = classes;
  }

  getUserRole() {
    const token = localStorage.getItem('authToken');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.get<any>('http://localhost:3000/api/employe/bytoken', { headers }).subscribe({
        next: (res) => {
          console.log('resss',res)
          this.userRole = res.user.role;
          console.log("Rôle de l'utilisateur :", this.userRole);
        },
        error: (err) => {
          console.error("Erreur lors de la récupération du rôle :", err);
        }
      });
    } else {
      console.warn("Aucun token trouvé dans le localStorage.");
    }
  } logout() {
    const token = localStorage.getItem('authToken');
    if (token) {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      this.http.post('http://localhost:3000/api/employe/logout', {}, { headers }).subscribe({
        next: () => {
          localStorage.removeItem('authToken');
          localStorage.removeItem('employe');
          this.router.navigate(['/auth/login']);
        },
        error: (err) => {
          console.error("Erreur lors de la déconnexion :", err);
          alert('Erreur lors de la déconnexion');
        }
      });
    }
  }
}
