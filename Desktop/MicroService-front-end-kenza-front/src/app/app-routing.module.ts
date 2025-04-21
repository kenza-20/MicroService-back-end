import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";


// layouts
import { AdminComponent } from "./layouts/admin/admin.component";
import { AuthComponent } from "./layouts/auth/auth.component";

// admin views
import { DashboardComponent } from "./views/admin/dashboard/dashboard.component";
import { MapsComponent } from "./views/admin/maps/maps.component";
import { SettingsComponent } from "./views/admin/settings/settings.component";
import { TablesComponent } from "./views/admin/tables/tables.component";
import { JobOffersComponent } from "./views/job-offers/job-offers.component";
import { AdminJobOffersComponent } from "./views/admin-job-offers/admin-job-offers.component";
import { JobOffersWrapperComponent } from './views/job-offers-wrapper/job-offers-wrapper.component';

// auth views
import { LoginComponent } from "./views/auth/login/login.component";
import { RegisterComponent } from "./views/auth/register/register.component";

// no layout views
import { IndexComponent } from "./views/index/index.component";
import { LandingComponent } from "./views/landing/landing.component";
import { ProfileComponent } from "./views/profile/profile.component";
// maram leave views
import { LeaveComponent } from "./layouts/leave/leave.component";
import { LeaveRequestComponent } from "./views/leave/request/leave-request.component";
import { LeaveRHComponent } from "./views/leave/rh/leave-rh.component";
import { EmployesComponent } from "./views/admin/employes/employes.component";
import {EmployeAddComponent} from "./components/employe/employe-add/employe-add.component";
import {EmployeModifyComponent} from "./components/employe/employe-modify/employe-modify.component";
import {EmployeListComponent} from "./components/employe/employe-list/employe-list.component";

const role = localStorage.getItem('user_role') || 'USER';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'tables', component: TablesComponent },
      { path: 'maps', component: MapsComponent },
      // ➕ Wrapper pour redirection automatique
      { path: 'job-offers', component: JobOffersWrapperComponent },

      // 👉 Les vraies routes pour chaque rôle
      { path: 'job-offers/admin', component: AdminJobOffersComponent },
      { path: 'job-offers/user', component: JobOffersComponent },



      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

      { path: 'add-employe', component: EmployeAddComponent },
      { path: 'modify-employe/:id', component: EmployeModifyComponent },
      { path: 'employes', component: EmployeListComponent },  // Liste des employés

    ],
  }
  ,
  {
    path: "auth",
    component: AuthComponent,
    children: [
      { path: "login", component: LoginComponent },
      { path: "register", component: RegisterComponent },
      { path: "", redirectTo: "login", pathMatch: "full" },
    ],
  },

  {
    path: "leave",
    component: LeaveComponent,
    children: [
      { path: "request", component: LeaveRequestComponent },
      { path: "rh", component: LeaveRHComponent },
      { path: "", redirectTo: "request", pathMatch: "full" },
    ],
  },
  {
    path: "paie",
    component: LeaveComponent,
    children: [
      { path: "employes", component: EmployesComponent },
      { path: "", redirectTo: "employes", pathMatch: "full" },
    ],
  },
  {
    path: "user",
    component: LeaveComponent,
    children: [
      { path: "profile", component: ProfileComponent },
      { path: "", redirectTo: "profile", pathMatch: "full" },
    ],
  },
  { path: "landing", component: LandingComponent },
  { path: "", redirectTo: "/auth/login", pathMatch: "full" },
  { path: "**", redirectTo: "", pathMatch: "full" },
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
