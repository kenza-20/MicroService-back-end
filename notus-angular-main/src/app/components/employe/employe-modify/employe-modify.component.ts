import { Component, OnInit } from '@angular/core';
import { EmployeService } from '../../service/employe.service';
import { Employe } from '../../../model/employe';
import { ActivatedRoute,Router } from '@angular/router';


@Component({
  selector: 'app-employe-modify',
  templateUrl: './employe-modify.component.html',
  styleUrls: ['./employe-modify.component.css']
})
export class EmployeModifyComponent implements OnInit {

  employe: Employe = {
    id: 0,
    name: '',
    description: '',
    role: '',
    grade: 0
  };

  constructor(private employeService: EmployeService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.getEmploye(id);
  }

  getEmploye(id: number): void {
    this.employeService.getEmployeById(id).subscribe(
      (data: Employe) => {
        this.employe = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération de l\'employé', error);
      }
    );
  }

  modifyEmploye(): void {
    this.employeService.modifyEmploye(this.employe).subscribe(
      (response) => {
        console.log('Employé modifié avec succès!', response);
        this.router.navigate(['/employes']);  // Rediriger vers la liste des employés
      },
      (error) => {
        console.error('Erreur lors de la modification de l\'employé', error);
      }
    );
  }
}
