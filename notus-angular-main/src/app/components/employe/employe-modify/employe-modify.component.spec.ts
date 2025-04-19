import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeModifyComponent } from './employe-modify.component';

describe('EmployeModifyComponent', () => {
  let component: EmployeModifyComponent;
  let fixture: ComponentFixture<EmployeModifyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeModifyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
