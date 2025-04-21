import { Component, OnInit,Input } from '@angular/core';
import { CongeService } from '../conge.service';

@Component({
  selector: 'app-leave-rh',
  templateUrl: './leave-rh.component.html',
  styleUrls: ['./leave-rh.component.css']
})
export class LeaveRHComponent implements OnInit {
  conges: any[] = [
    {
      id: 1,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'Vacation',
    statut: 'PENDING',
    hrComment: 'dqs'
  },
  {
    id: 2,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'Vacation',
    statut: 'APPROVED',
    hrComment: 'dqs'
  },
  {
    id: 3,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'Vacation',
    statut: 'APPROVED',
    hrComment: 'dqs'
  },
  {
    id: 4,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'Vacation',
    statut: 'REJECTED',
    hrComment: 'dqs'
  },
  {
    id: 5,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'VacationVacation Vacation Vacation Vacation Vacation VacationVacation Vacation  Vacation Vacation Vacation Vacation Vacation VacationVacation Vacation Vacation Vacation Vacation Vacation VacationVacation Vacation  Vacation Vacation Vacation Vacation Vacation VacationVacation Vacation  Vacation Vacation Vacation Vacation Vacation VacationVacation Vacation  Vacation Vacation Vacation VacationVacation',
    statut: 'APPROVED',
    hrComment: 'dqs'
  },{
    id: 6,
    employeId: 'John Doe',
    dateDebut: '2023-10-01',
    dateFin: '2023-10-05',
    motif: 'Vacation',
    statut: 'REJECTED',
    hrComment: 'dqs'
  },
  {
    id: 7,
  employeId: 'John Doe',
  dateDebut: '2023-10-01',
  dateFin: '2023-10-05',
  motif: 'Vacation',
  statut: 'PENDING',
  hrComment: 'dqs'
}, {
  id: 8,
employeId: 'John Doe',
dateDebut: '2023-10-01',
dateFin: '2023-10-05',
motif: 'Vacation',
statut: 'PENDING',
hrComment: 'dqs'
}
];


selectedConge: any = null;
showModal: boolean = false;

openModal(conge: any): void {
  this.selectedConge = conge;
  this.showModal = true;
}

closeModal(): void {
  this.showModal = false;
}

  responseMessage = '';

  
  @Input()
  get color(): string {
    return this._color;
  }
  set color(color: string) {
    this._color = color !== "light" && color !== "dark" ? "light" : color;
  }
  private _color = "light";

  constructor(private congeService: CongeService) { }

  ngOnInit(): void {
    this.loadConges();
  }

  loadConges(): void {
    this.congeService.getAllConges().subscribe({
      next: (data) => this.conges = data,
      error: (err) => console.error('Error fetching conge requests', err)
    });
  }

  updateStatus(congeId: number, statut: string, hrComment: string): void {
    this.congeService.updateCongeStatus(congeId, statut, hrComment).subscribe({
      next: (res) => {
        this.responseMessage = 'Conge status updated successfully !';
        this.loadConges();  // Refresh the list after update
      },
      error: (err) => this.responseMessage = 'Error updating conge status.'
    });
    
  }
}
