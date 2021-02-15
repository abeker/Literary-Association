import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistrateComponent } from './auth/registrate/registrate.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { BookComponent } from './pages/book/book.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { BookRequestsComponent } from './pages/editor/book-requests/book-requests.component';
import { ChangesApprovedComponent } from './pages/editor/changes-approved/changes-approved.component';
import { HandwriteUploadComponent } from './pages/handwrite-upload/handwrite-upload.component';
import { HandwriteChangeComponent } from './pages/handwrite-change/handwrite-change.component';
import { PlagiatsComponent } from './pages/editor/plagiats/plagiats.component';
import { FileUploadComponent} from './pages/file-upload/file-upload.component';
import { VoteComponent} from './pages/vote/vote.component';
import { BetaReaderListComponent } from './pages/beta-reader-list/beta-reader-list.component';
import { BetaReaderCommentComponent } from './pages/beta-reader-comment/beta-reader-comment.component';
import { BlockUserComponent } from './pages/block-user/block-user.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/registration', component: RegistrationComponent },
  { path: 'auth/registrate', component: RegistrateComponent },
  { path: 'auth/block', component: BlockUserComponent },
  { path: 'register/fileUpload', component: FileUploadComponent},
  {
    path: 'dashboard', component: DashboardComponent, children: [
      { path: 'welcome', component: WelcomeComponent },
      { path: 'book', component: BookComponent },
      { path: 'book-requests', component: BookRequestsComponent},
      { path: 'changes-approved', component: ChangesApprovedComponent},
      { path: 'handwrite-upload', component: HandwriteUploadComponent},
      { path: 'handwrite-change' , component: HandwriteChangeComponent}, 
      { path: 'plagiats', component: PlagiatsComponent},
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
