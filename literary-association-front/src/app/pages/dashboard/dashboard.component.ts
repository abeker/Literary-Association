import { animate, keyframes, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';

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
  public isEditor: boolean;
  public isLector: boolean;
  state = 'normal';

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.setupUserRole();
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  private setupUserRole(): void {
    this.setupUser();
    if(this.user.userRole === 'ADMIN'){
        this.setAllToFalse();
        this.isAdmin = true;
    } else if(this.user.userRole === 'WRITER'){
        this.setAllToFalse();
        this.isWriter = true;
    } else if(this.user.userRole === 'READER'){
        this.setAllToFalse();
        this.isReader = true;
    } else if(this.user.userRole === 'EDITOR'){
        this.setAllToFalse();
        this.isEditor = true;
    } else if(this.user.userRole === 'LECTOR'){
        this.setAllToFalse();
        this.isLector = true;
    }
  }

  setAllToFalse(): void {
      this.isAdmin = false;
      this.isWriter = false;
      this.isReader = false;
      this.isEditor = false;
      this.isLector = false;
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
}
