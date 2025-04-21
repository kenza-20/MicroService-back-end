import { Component, OnInit } from '@angular/core';
import { JobOffersService, JobOffer } from 'src/app/services/job-offers.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-job-offers',
  templateUrl: './job-offers.component.html',
})
export class JobOffersComponent implements OnInit {
  offers: JobOffer[] = [];
  selectedOfferId: number | null = null;
  applicationForm!: FormGroup;
  editingEmail: string | null = null;
  isHR: boolean = false;

  constructor(
    private jobOffersService: JobOffersService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const role = localStorage.getItem('user_role');
    this.isHR = role === 'HR';

    this.applicationForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      cvUrl: ['', Validators.required]
    });

    this.loadOffers();
  }

  loadOffers(): void {
    this.jobOffersService.getAllOffers().subscribe({
      next: (data) => {
        if (this.isHR) {
          this.offers = data;
        } else {
          const localApps = this.getLocalApplications();
          this.offers = data.map(offer => ({
            ...offer,
            applications: localApps.filter(app => app.offerId === offer.id)
          }));
        }
      },
      error: (err) => {
        console.error('Erreur de chargement :', err);
        alert('Impossible de récupérer les offres.');
      }
    });
  }

  openApplicationForm(offerId: number, emailToEdit?: string): void {
    this.selectedOfferId = offerId;
    this.editingEmail = emailToEdit || null;

    if (emailToEdit) {
      const app = this.getLocalApplications().find(
        a => a.offerId === offerId && a.email === emailToEdit
      );
      if (app) {
        this.applicationForm.setValue({
          fullName: app.fullName,
          email: app.email,
          cvUrl: app.cvUrl
        });
      }
    } else {
      this.applicationForm.reset();
    }
  }

  postuler(): void {
    if (!this.selectedOfferId || this.applicationForm.invalid) {
      alert('Veuillez remplir tous les champs.');
      return;
    }

    const app = {
      ...this.applicationForm.value,
      offerId: this.selectedOfferId
    };

    let localApps = this.getLocalApplications();

    if (this.editingEmail) {
      // Modifier localement
      localApps = localApps.map(a =>
        a.offerId === app.offerId && a.email === this.editingEmail ? app : a
      );
      alert('✏️ Candidature modifiée localement.');
    } else {
      // Postuler via backend
      this.jobOffersService.applyToOffer(this.selectedOfferId, app).subscribe({
        next: () => {
          alert('✅ Candidature envoyée avec succès.');
          localApps.push(app); // Stocker localement pour affichage côté user
          localStorage.setItem('applications', JSON.stringify(localApps));
          this.loadOffers();
        },
        error: (err) => {
          console.error('Erreur envoi :', err);
          alert('❌ Échec envoi candidature.');
        }
      });
      return; // Ne pas continuer après backend
    }

    localStorage.setItem('applications', JSON.stringify(localApps));
    this.applicationForm.reset();
    this.selectedOfferId = null;
    this.editingEmail = null;
    this.loadOffers();
  }

  supprimerCandidature(offerId: number, email: string): void {
    const updated = this.getLocalApplications().filter(
      a => !(a.offerId === offerId && a.email === email)
    );
    localStorage.setItem('applications', JSON.stringify(updated));
    alert('🗑️ Candidature supprimée localement.');
    this.loadOffers();
  }

  getLocalApplications(): any[] {
    return JSON.parse(localStorage.getItem('applications') || '[]');
  }
}
