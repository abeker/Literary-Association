import { animate, keyframes, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { LiteraryAssociationService } from './../../services/literary-association.service';
import { ReaderService } from './../../services/reader.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {

  isCollapsed = false;
  private user: any;
  public isAdmin: boolean = true;
  public isWriter: boolean;
  public isReader: boolean;
  public isBetaReader: boolean;
  public isEditor: boolean;
  public isLector: boolean;
  public isCommittee: boolean;
  state = 'normal';

  constructor(private router: Router, private literaryService: LiteraryAssociationService, private readerService: ReaderService) { }

  ngOnInit(): void {
    console.log("WELCOME ON DASHBOARD");
    this.setupUserRole();
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  private setupUserRole(): void {
    this.setupUser();
    console.log(this.user);
    console.log(this.user.userRole);
    if(this.user.userRole === 'ADMIN'){
        this.setAllToFalse();
        this.isAdmin = true;
    } else if(this.user.userRole === 'WRITER'){
        this.setAllToFalse();
        this.isWriter = true;
    } else if(this.user.userRole === 'READER'){
        this.setAllToFalse();
        this.isReader = true;
        this.checkIsBetaReader(this.user.username);
    } else if(this.user.userRole === 'EDITOR'){
        this.setAllToFalse();
        this.isEditor = true;
    } else if(this.user.userRole === 'LECTOR'){
        this.setAllToFalse();
        this.isLector = true;
    } else if(this.user.userRole === 'COMMITTE_MEMBER'){
        console.log("DOBRO POSTAVLJAM PATH");
        this.setAllToFalse();
        this.isCommittee = true;
        this.router.navigateByUrl('/dashboard/vote');
        console.log("DOBRO POSTAVLJAM PATH 2");

    }
  }

  setAllToFalse(): void {
      this.isAdmin = false;
      this.isWriter = false;
      this.isReader = false;
      this.isBetaReader = false;
      this.isEditor = false;
      this.isLector = false;
      this.isCommittee = false;
  }

  logout(): void {
    // this.store.dispatch(new AuthActions.Logout());
    this.router.navigateByUrl('/auth/login');
    localStorage.removeItem("user");
    // localStorage.removeItem("publishBookProccessId");
  }
  bookClick(): void {
    this.router.navigateByUrl('/dashboard/book');
  }
  bookRequests(): void {
    this.router.navigateByUrl('/dashboard/book-requests');
  }
  handwriteClick(): void {
    this.router.navigateByUrl('/dashboard/handwrite-upload');
  }
  checkForPlagiats(): void {
    this.router.navigateByUrl('/dashboard/plagiats');
  }
  
  chooseBetaReader(): void {
    //startujem moj proces
    this.literaryService.publishBook2Start().
    subscribe((res) => {
        console.log("start publish-2deo"+ res);
        localStorage.setItem("processInstance", res);
        this.router.navigateByUrl('/dashboard/betaReader');
    }, error => {
      console.log(error);
    }); 
  }


  betaReaderComment(): void {
    this.router.navigateByUrl('/dashboard/betaReaderComment');
  }

  
  checkIsBetaReader(username): void {
    this.readerService.isBetaReader(username).
    subscribe((res) => {
        console.log("isBetaReader"+ res);
        if(res){
          this.isBetaReader = true;
        }
    }, error => {
      console.log(error);
    }); 
  }


}
