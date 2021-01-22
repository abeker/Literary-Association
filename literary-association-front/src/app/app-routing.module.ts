import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistrateComponent } from './auth/registrate/registrate.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { BookComponent } from './pages/book/book.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { FileUploadComponent} from './pages/file-upload/file-upload.component';
import { VoteComponent} from './pages/vote/vote.component';
import { BetaReaderListComponent } from './pages/beta-reader-list/beta-reader-list.component';
import { BetaReaderCommentComponent } from './pages/beta-reader-comment/beta-reader-comment.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/registration', component: RegistrationComponent },
  { path: 'auth/registrate', component: RegistrateComponent },
  { path: 'register/fileUpload', component: FileUploadComponent},
  {
    path: 'dashboard', component: DashboardComponent, children: [
      { path: 'welcome', component: WelcomeComponent },
      { path: 'book', component: BookComponent },
      { path: 'vote', component: VoteComponent },
      { path: 'betaReader', component: BetaReaderListComponent},
      { path: 'betaReaderComment', component: BetaReaderCommentComponent}
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
