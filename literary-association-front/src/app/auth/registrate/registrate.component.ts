import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Observable, Observer } from 'rxjs';
import { NzMessageService, valueFunctionProp } from 'ng-zorro-antd';
import { UserService } from './../../services/user.service';
import { ReaderService } from './../../services/reader.service';


@Component({
  selector: 'app-registrate',
  templateUrl: './registrate.component.html',
  styleUrls: ['./registrate.component.css']
})
export class RegistrateComponent implements OnInit {

  validateForm: FormGroup;
  isUsernameExist: boolean = false;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  enumValues = [];
 

  constructor(private router: Router, private authService: AuthService, private fb: FormBuilder,
              private userService: UserService,  private message: NzMessageService, private readerService: ReaderService) { 
       
      this.validateForm = this.fb.group({});

      this.authService.startRegistrationProcess().
       subscribe((res) => {
          console.log(res);
          this.formFieldsDto = res;
          this.formFields = res.formFields;
          this.processInstance = res.processInstanceId;
          this.formFields.forEach( (field) =>{
            if( field.type.name=='enum'){
              this.enumValues = Object.keys(field.type.values);
            }
            let field_validations = field.validationConstraints;
            let validators = this.newValidationRule(field_validations);
            this.validateForm.addControl(field.id, this.fb.control('', validators)); 
          });
       }, error => {
        console.log(error);
      });
  }


  ngOnInit(): void {
  }


  submitForm(value){
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsDirty();
      this.validateForm.controls[key].updateValueAndValidity();
    }

    let o = new Array();
    for (var property in value) {
      o.push({fieldId : property, fieldValue : value[property]});
    }

    this.authService.registerUser(o,this.formFieldsDto.taskId)
      .subscribe(response => {
        console.log(response);
        console.log('REGISTER SUCCES');
        alert("You registered successfully!")
      }, error => {
        console.log("Error occured!");
      })

    this.resetForm(new MouseEvent('click'));
  }


  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    this.isUsernameExist = false;
  }

  
  newValidationRule(field_validations): any {
    let validators = [];
    field_validations.forEach(tep => {
      switch(tep.name) {
        case "required":
          validators.push(Validators.required);
          break;
        case "minlength":
          validators.push(Validators.minLength(tep.configuration));
          break;
        case 'maxlength':
          validators.push(Validators.maxLength(tep.configuration));
          break;
      }
    });
    return validators;
  }


  confirmValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { error: true, required: true };
    } else if (control.value !== this.validateForm.controls.password.value) {
      return { confirm: true, error: true };
    }
    return {};
  };


  validateConfirmPassword(): void {
    setTimeout(() => this.validateForm.controls.confirm.updateValueAndValidity());
  }


  userNameAsyncValidator = (control: FormControl) =>
    new Observable((observer: Observer<ValidationErrors | null>) => {
      this.userService.getUser(control.value).subscribe(() => {
        this.isUsernameExist = true;
      }, () => {
        this.isUsernameExist = false;
      });

      setTimeout(() => {
        if (this.isUsernameExist) {
          observer.next({ error: true, duplicated: true });
        } else {
          observer.next(null);
        }
        observer.complete();
      }, 1000);
    });



   //Its a bad practice to use expressions in angular bindings, so move the class expression into controller.
  isPassword(item): boolean {
    return (item.id === 'password' || item.id === "c_password");
  }
  
  isString(item) : boolean {
    return (item.type.name === 'string' && !this.isPassword(item));
  }


  onLogin(): void {
    this.router.navigateByUrl('auth/login');
  }

  onPasswordChange(passwordInput): void {
    
  }
}
