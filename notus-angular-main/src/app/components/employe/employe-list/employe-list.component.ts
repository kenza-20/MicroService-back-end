import { Component, OnInit } from '@angular/core';
import { EmployeService } from '../../service/employe.service';
import { Employe } from '../../../model/employe';

@Component({
  selector: 'app-employe-list',
  templateUrl: './employe-list.component.html',
  styleUrls: ['./employe-list.component.css']
})
export class EmployeListComponent implements OnInit {

  employes: Employe[] = [];

  constructor(private employeService: EmployeService) { }

  ngOnInit(): void {
    this.getEmployes();
  }

  getEmployes(): void {
    this.employeService.getEmployes().subscribe(
      (data: Employe[]) => {
        this.employes = data;
      },
      (error) => {
        console.error('Error fetching employes:', error);
      }
    );
  }
  deleteEmploye(id: number): void {
    this.employeService.removeEmploye(id).subscribe(
      () => {
        this.employes = this.employes.filter(emp => emp.id !== id); // Remove the deleted employee from the list
        console.log('Employé supprimé avec succès!');
      },
      (error) => {
        console.error('Erreur lors de la suppression de l\'employé', error);
      }
    );
  }

}
