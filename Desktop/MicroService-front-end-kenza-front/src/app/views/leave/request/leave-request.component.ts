import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CongeService } from '../conge.service';

interface Conge {
  employeId: string;
  dateDebut: string;
  dateFin: string;
}

@Component({
  selector: 'app-leave-request',
  templateUrl: './leave-request.component.html',
  styleUrls: ['./leave-request.component.css']
})
export class LeaveRequestComponent implements OnInit {
  congeForm!: FormGroup;
  responseMessage: string = '';
  isError: boolean = false;
  existingConges: Conge[] = [];

  constructor(private fb: FormBuilder, private congeService: CongeService) {}

  ngOnInit(): void {
    this.congeForm = this.fb.group({
      employeId: ['', Validators.required],
      dateDebut: ['', Validators.required],
      dateFin: ['', Validators.required],
      motif: ['', Validators.required]
    });

    // Fetch existing leaves when the component loads
    this.fetchExistingConges();
  }

  fetchExistingConges(): void {
    this.congeService.getAllConges().subscribe({
      next: (data: Conge[]) => this.existingConges = data,
      error: () => console.error("Failed to fetch existing leaves")
    });
  }

  onSubmit(): void {
    if (this.congeForm.invalid) {
      this.responseMessage = 'Please fill in all required fields correctly.';
      this.isError = true;
      return;
    }

    const { employeId, dateDebut, dateFin } = this.congeForm.value;
    const startDate = new Date(dateDebut);
    const endDate = new Date(dateFin);

    if (startDate > endDate) {
      this.responseMessage = 'Error: Start date cannot be after End date.';
      this.isError = true;
      return;
    }

    // Check for overlapping leave requests
    if (this.isOverlappingLeave(employeId, startDate, endDate)) {
      this.responseMessage = 'Error: Requested leave period overlaps with an existing leave.';
      this.isError = true;
      return;
    }

    // Proceed with the request
    this.congeService.requestConge(employeId, dateDebut, dateFin, this.congeForm.value.motif)
      .subscribe({
        next: () => {
          this.responseMessage = 'Leave request submitted successfully!';
          this.isError = false;
          this.congeForm.reset();
          this.fetchExistingConges(); // Refresh the list after submitting
        },
        error: () => {
          this.responseMessage = 'There was an error submitting your leave request. Please try again.';
          this.isError = true;
        }
      });
  }

  isOverlappingLeave(employeId: string, startDate: Date, endDate: Date): boolean {
    return this.existingConges.some(conge => 
      conge.employeId === employeId &&
      (
        (startDate >= new Date(conge.dateDebut) && startDate <= new Date(conge.dateFin)) ||
        (endDate >= new Date(conge.dateDebut) && endDate <= new Date(conge.dateFin)) ||
        (startDate <= new Date(conge.dateDebut) && endDate >= new Date(conge.dateFin))
      )
    );
  }
}