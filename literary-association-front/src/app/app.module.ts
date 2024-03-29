import { CommonModule, registerLocaleData } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import en from '@angular/common/locales/en';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { IconDefinition } from '@ant-design/icons-angular';
import * as AllIcons from '@ant-design/icons-angular/icons';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { AngularSplitModule } from 'angular-split';
import { NgZorroAntdModule, NzFormModule, NzIconModule } from 'ng-zorro-antd';
import { en_US, NZ_I18N } from 'ng-zorro-antd/i18n';
import { environment } from './../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { RegistrateComponent } from './auth/registrate/registrate.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { BookComponent } from './pages/book/book.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { FileUploadComponent } from './pages/file-upload/file-upload.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { BookRequestsComponent } from './pages/editor/book-requests/book-requests.component';
import { HandwriteUploadComponent } from './pages/handwrite-upload/handwrite-upload.component';
import { PlagiatsComponent } from './pages/editor/plagiats/plagiats.component';
import { VoteComponent } from './pages/vote/vote.component';
import { BetaReaderListComponent } from './pages/beta-reader-list/beta-reader-list.component';
import { BetaReaderCommentComponent } from './pages/beta-reader-comment/beta-reader-comment.component';
import { BlockUserComponent } from './pages/block-user/block-user.component';
import { HandwriteChangeComponent } from './pages/handwrite-change/handwrite-change.component';
import { ChangesApprovedComponent } from './pages/editor/changes-approved/changes-approved.component';

registerLocaleData(en);

const antDesignIcons = AllIcons as {
  [key: string]: IconDefinition;
};
const icons: IconDefinition[] = Object.keys(antDesignIcons).map(key => antDesignIcons[key])

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    WelcomeComponent,
    RegistrationComponent,
    RegistrateComponent,
    BookComponent,
    FileUploadComponent,
    BookRequestsComponent,
    HandwriteUploadComponent,
    PlagiatsComponent,
    VoteComponent,
    BetaReaderListComponent,
    BetaReaderCommentComponent,
    BlockUserComponent,
    HandwriteChangeComponent,
    ChangesApprovedComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    NgZorroAntdModule,
    NzFormModule,
    AppRoutingModule,
    NzIconModule,
    AngularSplitModule.forRoot(),
    CommonModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    StoreDevtoolsModule.instrument({ logOnly: environment.production }),
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
