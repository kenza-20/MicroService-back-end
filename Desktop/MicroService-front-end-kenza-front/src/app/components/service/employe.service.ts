// @ts-ignore
import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from "rxjs";
import { Employe } from '../../model/employe';  // Assuming you have an Employe model


@Injectable({
  providedIn: 'root'
})
export class EmployeService {

  private apiUrl = 'http://localhost:8093/api/employes'; // Spring Boot backend URL

  constructor(private http: HttpClient) { }

  // Get all employes
  getEmployes(): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.apiUrl}/retrieve-all-employes`);
  }

  // Get a single employe by ID
  getEmployeById(id: number): Observable<Employe> {
    return this.http.get<Employe>(`${this.apiUrl}/retrieve-employe/${id}`);
  }

  // Add a new employe
  addEmploye(employe: Omit<Employe, 'id'>): Observable<Employe> {
    return this.http.post<Employe>(`${this.apiUrl}/add-employe`, employe);
  }


  // Remove an employe
  removeEmploye(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/remove-employe/${id}`);
  }

  // Modify an existing employe
  modifyEmploye(employe: Employe): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/modify-employe`, employe);
  }
}
