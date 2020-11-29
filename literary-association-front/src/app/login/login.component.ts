import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent implements OnInit {

  constructor(private router: Router, private loginService: LoginService, private fb: FormBuilder,) { }

  @Input('name') username : string;
  @Input('name') password : string;
 
  ngOnInit(): void {
    
  }

  submitForm(): void {
    let obj = {
        "username":this.username,
        "password":this.password
    }
    console.log("ovdeee"+this.username);
    
    this.loginService.login(obj).subscribe(data => {
      console.log(data);
    });

  }
 


}
