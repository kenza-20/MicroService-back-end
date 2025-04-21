import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  employe = {
    name: '',
    email: '',
    password: '',
  };

  constructor(private http: HttpClient,private router: Router) {}

  ngOnInit(): void {}

  register() {
    console.log(this.employe);
    this.http.post('http://localhost:3000/api/employe/signup', this.employe).subscribe({
      next: (res) => {
        console.log('Employé inscrit avec succès', res);
        // 🔒 Enregistrement dans le localStorage
        localStorage.setItem('employe', JSON.stringify(this.employe));
        alert('Compte créé !');
        this.router.navigate(['/auth/login']); // ✅ Redirection ici

      },
      error: (err) => {
        console.error('Erreur d\'inscription', err);
        alert('Erreur lors de la création du compte');
      },
    });
  }

}
