import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
    private router: Router) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();

      this.authService.login({
        username: this.validateForm.value.username,
        password: this.validateForm.value.password
      }).subscribe(user => {
        this.router.navigateByUrl('dashboard/welcome');
        localStorage.setItem('user', JSON.stringify(user));
      }, error => {
        console.log(error);
      });
    }
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
