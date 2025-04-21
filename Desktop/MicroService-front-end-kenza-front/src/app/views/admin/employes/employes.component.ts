import { Component, Input, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-employes',
  templateUrl: './employes.component.html',
  styleUrls: ['./employes.component.css']
})
export class EmployesComponent implements OnInit {
  @Input()
  get color(): string {
    return this._color;
  }
  set color(color: string) {
    this._color = color !== "light" && color !== "dark" ? "light" : color;
  }
  private _color = "light";

  employes: any[] = [];

  public apiUrl = 'http://localhost:8093/api/employes/retrieve-all-employes'; // adapte si besoin
  public modalOpen: any = false;
  historiqueData: any[] = [];
  showHistoriqueModal= false;

  selectedEmployeeId: number | null = null;
  paieData = {
    heuresTravaillees: null,
    tauxHoraire:null ,
    prime: null,
    deduction: null,
    acompte: null,
    email: ''
  };
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAll();
  }

  getAll(): void {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.employes = data;
        console.log('Employés récupérés :', data);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des employés', err);
      }
    });
  }

  openModal(emp: any) {
    console.log('Ouverture du modal pour:', emp);
    this.selectedEmployeeId = emp.id;
    this.paieData.email = emp.email;
    this.modalOpen = true;
    console.log('Modal ouverte:', this.modalOpen); // Debug
  }



  closeModal() {
    console.log('Modal fermée'); // Debug
    this.modalOpen = false;
    this.resetForm();
  }


  resetForm() {
    this.paieData = {
      heuresTravaillees: 0,
      tauxHoraire: 0,
      prime: 0,
      deduction: 0,
      acompte: 0,
      email: ''
    };
  }


  calculerPaie() {
    if (!this.selectedEmployeeId) return;

    const { heuresTravaillees, tauxHoraire, prime, deduction, acompte, email } = this.paieData;

    const params = new HttpParams()
      .set('employeeId', this.selectedEmployeeId.toString())
      .set('heuresTravaillees', (heuresTravaillees || 0).toString())
      .set('tauxHoraire', (tauxHoraire || 0).toString())
      .set('prime', (prime || 0).toString())
      .set('deduction', (deduction || 0).toString())
      .set('acompte', (acompte || 0).toString())
      .set('email', email || '');

    this.http.post('http://localhost:8093/api/paie/calcul', null, {
      params,
      responseType: 'blob'
    }).subscribe({
      next: (response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'bulletin-paie.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
        this.closeModal();
      },
      error: (err) => {
        console.error('Erreur lors du téléchargement du PDF:', err);
      }
    });
  }



  voirHistorique(employeeId: number) {
    this.http.get<any[]>(`http://localhost:8093/api/paie/historique/${employeeId}`).subscribe({
      next: (data) => {
        console.log('Historique:', data);
        this.historiqueData = data;
        this.showHistoriqueModal = true;
      },
      error: (err) => {
        console.error('Erreur historique:', err);
      }
    });
  
  }
  fermerModal() {
    this.showHistoriqueModal = false;
    this.historiqueData = [];
  }
}
