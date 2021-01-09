import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { RegistrateComponent } from './auth/registrate/registrate.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { SellerRegistrationComponent } from './pages/seller-registration/seller-registration.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/registration', component: RegistrationComponent },
  { path: 'auth/seller-registration', component: SellerRegistrationComponent },
  { path: 'auth/registrate', component: RegistrateComponent },
  {
    path: 'dashboard', component: DashboardComponent, children: [
      { path: 'welcome', component: WelcomeComponent }
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [
  CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
