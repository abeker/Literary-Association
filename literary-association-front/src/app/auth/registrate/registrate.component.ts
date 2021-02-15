import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';
import { NzSelectSizeType } from 'ng-zorro-antd/select';
import { Observable, Observer } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { ReaderService } from './../../services/reader.service';
import { UserService } from './../../services/user.service';

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
              private userService: UserService, private message: NzMessageService, private readerService: ReaderService, private http: HttpClient) {

      this.validateForm = this.fb.group({});
      // this.http.get("http://localhost:8084/welcome/startProcess").subscribe(resp => {
      // console.log("PROCESS INSTANCE: " + resp)
      // localStorage.setItem("processInstance",  resp.toString())
      // this.http.get("http://localhost:8084/welcome/startProcess").toPromise().then(resp=>{
      //   console.log(resp)
      // })

  }


  ngOnInit(): void {
    let proecesInstanceId = localStorage.getItem("processInstance");
    let registrationType = localStorage.getItem("registrationType");
    console.log("ISCITAVAM: " + proecesInstanceId + "TIPA: " + registrationType);
    this.authService.startRegistrationProcess(proecesInstanceId).
    subscribe((res) => {
        // console.log(res);
        this.initFieldsDto = res;
        this.formFieldsDto = res;
        this.changeFormFieldsDTO();
        //console.log(this.formFields);
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          //console.log(field);
          let field_validations = field.validationConstraints;
          let validators = this.newValidationRule(field_validations, field);
          this.validateForm.addControl(field.id, this.fb.control('', validators));
         //console.log(field.id, validators);
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

      // console.log("SUBMIT");
        let o = new Array();
        for (var property in value) {
          if(property === "betaReader"){
              o.push({fieldId : property, fieldValue : this.isBetareader});
          }else if (property === "genre" || property === "genres"){
              let genreString = "";
              for(let i=0;i<this.multipleValue.length;i++){
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

        if(this.isBetareader && this.multipleValue.length === 0){   ///ZA BETA READERA IZBACITI JOS NEKI BOX GDE CE PONOVO BIRATI??
              alert("Please select some genre");
        }else if(this.multipleValue.length === 0){
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



  newValidationRule(field_validations, field): any {
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
    if(field.type.name == "confirm_password"){
         validators.push(this.confirmValidator);
    }else if(field.properties.hasOwnProperty('email')){
         validators.push(Validators.email);
         //validators.push(this.userNameAsyncValidator);
    }else if(field.type.name == "password" && field.properties.hasOwnProperty('pattern') ){
         let obj = field.properties;
         validators.push(Validators.pattern(new RegExp(obj['pattern'])));
    }
    return validators;
  }


   confirmValidator = (control: FormControl): { [s: string]: boolean } => {
     //console.log("USLAA OVDE");
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

  afterClose(): void {
    console.log('close');
  }


  onLogin(): void {
    this.router.navigateByUrl('auth/login');
  }


  onPasswordChange(passwordInput): void {
  }

  onPasswordConfirmChange(passwordInput): void {
  }

  onFileChange(event){
  }



}
