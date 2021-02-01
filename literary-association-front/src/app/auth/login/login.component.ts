import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  validateForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private message: NzMessageService) {}

  ngOnInit(): void {
    const isUserBlocked = localStorage.getItem('userBlocked');
    if(isUserBlocked) {
      this.router.navigateByUrl('auth/block');
    }

    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.authService.login({
      username: this.validateForm.value.username,
      password: this.validateForm.value.password
    }).subscribe(user => {
      this.router.navigateByUrl('dashboard/welcome');
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.removeItem('userBlocked');
    }, error => {
      if(error.status === 400) {
        this.message.error("Bad credentials");
      } else if(error.status === 409) {
        this.message.error("You have reached your logging limit, please try again later.");
        localStorage.setItem('userBlocked', '1');
        this.router.navigateByUrl('auth/block');
      }
    });
  }

  register(): void {
    this.authService.startProcess().subscribe((data: string)=>{
      console.log("PROCES_INSTANCE" + data);
      localStorage.setItem("processInstance", data);
      localStorage.setItem("registrationType","reader");
      this.router.navigateByUrl('auth/registrate');
    })
  }

  registerAsWriter(): void {
    this.authService.startWriterRegistrationProcess().subscribe((data: string)=>{
      console.log("PROCES_INSTANCE" + data);
      localStorage.setItem("processInstance", data);
      localStorage.setItem("registrationType","writer");
      this.router.navigateByUrl('auth/registrate');
    })
  }

  registerAsSeller(): void {
    this.router.navigateByUrl('auth/seller-registration');
  }



}
