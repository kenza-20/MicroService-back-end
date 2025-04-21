import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CongeService {
  private baseUrl = 'http://localhost:8085/GestionRHEtPaie/conge';

  constructor(private http: HttpClient) {}

  // ✅ Pour les employés : Demander un congé
  requestConge(employeId: number, dateDebut: string, dateFin: string, motif: string): Observable<any> {
    const url = `${this.baseUrl}/request?employeId=${employeId}&dateDebut=${dateDebut}&dateFin=${dateFin}&motif=${motif}`;
    return this.http.post(url, {}); // corps vide car on utilise des query params
  }

  // ✅ Pour les RH : Récupérer tous les congés
  getAllConges(): Observable<any[]> {
    const url = `${this.baseUrl}/findAll`;
    return this.http.get<any[]>(url);
  }

  // ✅ Pour les RH : Mettre à jour le statut
  updateCongeStatus(congeId: number, statut: string, hrComment: string): Observable<any> {
    const url = `${this.baseUrl}/updateStatus?congeId=${congeId}&statut=${statut}&hrComment=${hrComment}`;
    return this.http.put(url, {});
  }
}
