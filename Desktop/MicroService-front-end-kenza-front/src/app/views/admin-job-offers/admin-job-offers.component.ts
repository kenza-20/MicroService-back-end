import { Component, OnInit } from '@angular/core';
import { JobOffersService, JobOffer } from 'src/app/services/job-offers.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin-job-offers',
  templateUrl: './admin-job-offers.component.html',
})
export class AdminJobOffersComponent implements OnInit {
  offers: JobOffer[] = [];
  offerForm!: FormGroup;
  editingOfferId: number | null = null;

  constructor(
    private jobOffersService: JobOffersService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.offerForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      location: ['', Validators.required],
      company: [''],
      salary: ['']
    });

    this.loadOffers();
  }

  loadOffers(): void {
    this.jobOffersService.getAllOffers().subscribe({
      next: (data) => {
        this.offers = data;
      },
      error: (err) => {
        console.error('❌ Erreur chargement :', err);
        alert('Impossible de charger les offres.');
      }
    });
  }

  editOffer(offer: JobOffer): void {
    this.editingOfferId = offer.id;
    this.offerForm.patchValue(offer);
  }

  cancelEdit(): void {
    this.editingOfferId = null;
    this.offerForm.reset();
  }

  submitOffer(): void {
    if (this.offerForm.invalid) return;

    const formValue = this.offerForm.value;

    if (this.editingOfferId) {
      this.jobOffersService.updateOffer(this.editingOfferId, formValue).subscribe({
        next: () => {
          alert('✅ Offre mise à jour');
          this.editingOfferId = null;
          this.offerForm.reset();
          this.loadOffers();
        },
        error: (err) => {
          console.error('❌ Erreur maj :', err);
          alert('Erreur mise à jour de l\'offre');
        }
      });
    } else {
      this.jobOffersService.createOffer(formValue).subscribe({
        next: () => {
          alert('✅ Offre ajoutée');
          this.offerForm.reset();
          this.loadOffers();
        },
        error: (err) => {
          console.error('❌ Erreur ajout :', err);
          alert('Erreur création de l\'offre');
        }
      });
    }
  }

  deleteOffer(id: number): void {
    if (!confirm("Supprimer cette offre ?")) return;

    this.jobOffersService.deleteOffer(id).subscribe({
      next: () => {
        alert('🗑️ Offre supprimée');
        this.loadOffers();
      },
      error: (err) => {
        console.error('❌ Erreur suppression :', err);
        alert('Erreur suppression');
      }
    });
  }
}
