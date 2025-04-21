import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    public fb: FormBuilder,
    public http: HttpClient,
    public router: Router
  ) {
    this.loginForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  login() {
    const credentials = this.loginForm.value;
    console.log('Credentials:', credentials); // 🐞 vérifie les valeurs
    this.http.post<any>('http://localhost:3000/api/employe/login', credentials).subscribe({
      next: (res) => {
        this.saveToken(res.token);
        this.router.navigate(['/user/profile']);
      },
      error: (err) => {
        alert('Erreur de connexion');
        console.error(err);
      }
    });
  }

  saveToken(token: string) {
    localStorage.setItem('authToken', token);
  }
}
