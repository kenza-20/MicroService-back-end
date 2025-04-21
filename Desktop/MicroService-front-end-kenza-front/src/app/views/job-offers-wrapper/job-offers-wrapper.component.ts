import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-job-offers-wrapper',
  template: '',
})
export class JobOffersWrapperComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {
    const role = localStorage.getItem('user_role');
    if (role === 'HR') {
      this.router.navigate(['/admin/job-offers/admin']);
    } else {
      this.router.navigate(['/admin/job-offers/user']);
    }
  }
}
