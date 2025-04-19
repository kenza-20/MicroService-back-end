import { Component, OnInit } from '@angular/core';
import { EmployeService } from '../../service/employe.service';
import { Employe } from '../../../model/employe';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employe-add',
  templateUrl: './employe-add.component.html',
  styleUrls: ['./employe-add.component.css']
})
export class EmployeAddComponent  {

  employe: Omit<Employe, 'id'> = {
    name: '',
    description: '',
    role: '',
    grade: 0
  };
  

  constructor(private employeService: EmployeService, private router: Router) { }

  addEmploye(): void {
    this.employeService.addEmploye(this.employe).subscribe(
      (response) => {
        console.log('Employé ajouté avec succès!', response);
        this.router.navigate(['/employes']);  // Rediriger vers la liste des employés
      },
      (error) => {
        console.error('Erreur lors de l\'ajout de l\'employé', error);
      }
    );
  }
}
