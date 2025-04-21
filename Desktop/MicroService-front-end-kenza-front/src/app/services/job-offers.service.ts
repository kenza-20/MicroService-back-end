import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface JobOffer {
  id: number;
  title: string;
  description: string;
  location: string;
  company?: string;
  salary?: string;
  createdAt?: string;
  updatedAt?: string;
  applications?: Application[];
}

export interface Application {
  id?: number;
  fullName: string;
  email: string;
  cvUrl: string;
  user?: {
    username: string;
  };
}

@Injectable({
  providedIn: 'root',
})
export class JobOffersService {
  private apiUrl = '/api/joboffers';

  constructor(private http: HttpClient) {}

  // 🔐 Headers GET/DELETE
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // 🔐 Headers POST/PUT
  private getJsonHeaders(): HttpHeaders {
    const token = localStorage.getItem('auth_token');
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // 🔎 Toutes les offres
  getAllOffers(): Observable<JobOffer[]> {
    return this.http.get<JobOffer[]>(this.apiUrl, {
      headers: this.getAuthHeaders(),
    });
  }

  // ➕ Créer une offre
  createOffer(offer: JobOffer): Observable<JobOffer> {
    return this.http.post<JobOffer>(this.apiUrl, offer, {
      headers: this.getJsonHeaders(),
    });
  }

  // ✏️ Modifier une offre
  updateOffer(id: number, offer: JobOffer): Observable<JobOffer> {
    return this.http.put<JobOffer>(`${this.apiUrl}/${id}`, offer, {
      headers: this.getJsonHeaders(),
    });
  }

  // 📨 Postuler à une offre
  applyToOffer(jobId: number, application: Application): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${jobId}/apply`, application, {
      headers: this.getJsonHeaders(),
    });
  }

  // 🗑️ Supprimer une offre
  deleteOffer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }

  // 🔧 Supprimer une candidature LOCAL
  removeLocalApplication(offers: JobOffer[], offerId: number, email: string) {
    const offer = offers.find(o => o.id === offerId);
    if (offer && offer.applications) {
      offer.applications = offer.applications.filter(app => app.email !== email);
    }
  }

  // ✏️ Modifier une candidature LOCAL
  updateLocalApplication(offers: JobOffer[], offerId: number, email: string, newApp: Application) {
    const offer = offers.find(o => o.id === offerId);
    if (offer && offer.applications) {
      const index = offer.applications.findIndex(app => app.email === email);
      if (index !== -1) {
        offer.applications[index] = { ...offer.applications[index], ...newApp };
      }
    }
  }
}
