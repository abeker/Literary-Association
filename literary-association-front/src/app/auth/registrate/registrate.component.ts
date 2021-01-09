import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { NzSelectSizeType } from 'ng-zorro-antd/select';
import { AuthService } from '../../services/auth.service';
import { Observable, Observer } from 'rxjs';
import { NzMessageService, valueFunctionProp } from 'ng-zorro-antd';
import { UserService } from './../../services/user.service';
import { ReaderService } from './../../services/reader.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-registrate',
  templateUrl: './registrate.component.html',
  styleUrls: ['./registrate.component.css']
})
export class RegistrateComponent implements OnInit {

  validateForm: FormGroup;
  isUsernameExist: boolean = false;
  initFieldsDto = null;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";

  isBetareader: boolean = false;
  listOfOption: Array<{ label: string; value: string }> = []; //genres
  size: NzSelectSizeType = 'default';
  multipleValue = []; //selected values of genre

  isPasswordCorrect: boolean = false;
  passwordPercentage: number = 0;
  passwordConfirmPercentage: number = 0;
  processInstace: any

  constructor(private router: Router, private authService: AuthService, private fb: FormBuilder,
              private userService: UserService,  private message: NzMessageService, private readerService: ReaderService, private http: HttpClient) { 
       
      this.validateForm = this.fb.group({});
      // this.http.get("http://localhost:8084/welcome/startProcess").subscribe(resp => {
      // console.log("PROCESS INSTANCE: " + resp)
      // localStorage.setItem("processInstance",  resp.toString())
      // this.http.get("http://localhost:8084/welcome/startProcess").toPromise().then(resp=>{
      //   console.log(resp)
      // })
        
  }


  ngOnInit(): void {
    let proecesInstanceId = localStorage.getItem("processInstance")
    console.log("ISCITAVAM: " + proecesInstanceId)
    this.authService.startRegistrationProcess(proecesInstanceId).
    subscribe((res) => {
        console.log(res);
        this.initFieldsDto = res;
        this.formFieldsDto = res;
        this.changeFormFieldsDTO();
        console.log(this.formFields);
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          console.log(field);
          let field_validations = field.validationConstraints;
          let validators = this.newValidationRule(field_validations);
          if(field.type.name == "confirm_password"){
            //this.validateForm.addControl(field.id, this.fb.control('', validators,[this.confirmValidator]));
            validators.push(this.confirmValidator);
          }else if(field.properties.hasOwnProperty('email')){
            validators.push(Validators.email);
            // validators.push(this.userNameAsyncValidator);
          }else if(field.type.name == "password" && field.properties.hasOwnProperty('pattern') ){
            let obj = field.properties;
            validators.push(Validators.pattern(new RegExp(obj['pattern'])));
          }
          this.validateForm.addControl(field.id, this.fb.control('', validators)); 
          console.log(field.id, validators);
        });
    }, error => {
      console.log(error);
    });
   
  }


  submitForm(value){
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsDirty();
      this.validateForm.controls[key].updateValueAndValidity();
    }

    console.log("SUBMIT");
    let o = new Array();
    for (var property in value) {
      //console.log(value);
     // console.log(property);
      if(property === "betaReader"){
        o.push({fieldId : property, fieldValue : this.isBetareader});
      }else if (property === "genre"){
        console.log("ZANROVIIIII");
        let genreString = "";
        
        for(let i=0;i<this.multipleValue.length;i++){
              //console.log(this.multipleValue[i]);
              genreString += (this.multipleValue[i]);
              genreString += (";");
        }
       // console.log(genreString);
        o.push({fieldId : property, fieldValue : genreString});
      }else{
        o.push({fieldId : property, fieldValue : value[property]});
      }
    }
    
    console.log(o);
    //console.log(this.multipleValue); 

    if(this.isBetareader && this.multipleValue.length === 0){
          alert("Please select some genre");
    }else{
        this.authService.registerUser(o,this.formFieldsDto.taskId)
        .subscribe(response => {
          console.log(response);
          console.log('REGISTER SUCCES');
          alert("You registered successfully! Please check email to confirm registration!")
        }, error => {
          console.log("Error occured!");
      })

      this.resetForm(new MouseEvent('click'));
      }

  }


  resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    this.isUsernameExist = false;
    this.isBetareader = false;
    this.multipleValue = [];
  }

  
  changeFormFieldsDTO(){
    let formFields = this.formFieldsDto.formFields;
    let copyFormFields = [];
    formFields.forEach(field => {
         let prop = field.properties;
         let temp = field;
         if(prop.hasOwnProperty('enum')){
            this.createListForOption(field.defaultValue);
            temp.type.name = "enum";
            temp.type.values = this.listOfOption;
         }else if(prop.hasOwnProperty('password')){
            temp.type.name = "password";
         }else if(prop.hasOwnProperty('confirm_password')){
            temp.type.name = "confirm_password";
         }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
  }

  

   createListForOption(genresString){
      const children: Array<{ label: string; value: string }> = [];
      let genres = genresString.split(";");
      for (let i = 0; i < genres.length-1; i++) {
        children.push({ label: genres[i] , value: genres[i]});
      }
      this.listOfOption = children;
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
     console.log("USLAA OVDE");
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
      console.log("VOLIM TE");
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

  afterClose(): void {
    console.log('close');
  }


  onLogin(): void {
    this.router.navigateByUrl('auth/login');
  }


  onPasswordChange(passwordInput): void {
    this.checkPassword(passwordInput, false);
  }

  onPasswordConfirmChange(passwordInput): void {
    this.checkPassword(passwordInput, true);
  }

  checkPassword(password, isConfirm: boolean): void {
    let coefficientAccuracy = 0;
    /*if(hasLowerCase(password)) {
      coefficientAccuracy += 1;
    } if(hasUpperCase(password)) {
      coefficientAccuracy += 1;
    } if(hasNumber(password)) {
      coefficientAccuracy += 1;
    } if(hasSpecialCharacter(password)) {
      coefficientAccuracy += 1;
    } if(hasMinLength(password)) {
      coefficientAccuracy += 1;
    }*/ 
    if(password === this.validateForm.value.password && isConfirm) {
      coefficientAccuracy += 1;
    }

    if(!isConfirm) {
      this.passwordPercentage = coefficientAccuracy * 100;
    } else {
      this.passwordConfirmPercentage = coefficientAccuracy * 100;
    }

  }

  

}



function hasLowerCase(str) {
  if(str != null) {
    return (/[a-z]/.test(str));
  }
}

function hasUpperCase(str) {
  if(str != null) {
    return (/[A-Z]/.test(str));
  }
}

function hasNumber(str) {
  if(str != null) {
    return (/[0-9]/.test(str));
  }
}

function hasSpecialCharacter(str) {
  if(str != null) {
    return (/[!@#$%^&.]/.test(str));
  }
}

function hasMinLength(str: string) {
  if(str != null) {
    return (str.length >= 9);
  }
}