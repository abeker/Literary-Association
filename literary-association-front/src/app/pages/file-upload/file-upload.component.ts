import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FilesService } from './../../services/files.service';

@Component({
  selector: 'app-registrate',
  templateUrl: './../../auth/registrate/registrate.component.html',
  styleUrls: ['./../../auth/registrate/registrate.component.css']
})
export class FileUploadComponent implements OnInit {

  validateForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";

  myFile:string [] = [];
  myFiles = new Map<string, string[]>();

  constructor(private router: Router, private filesService: FilesService, private http: HttpClient, private fb: FormBuilder) {
      this.validateForm = this.fb.group({});
   }

  ngOnInit(): void {
    let proecesInstanceId = localStorage.getItem("processInstance");
    console.log("PROCES FILE UPLOAD-A: " + proecesInstanceId);
    this.filesService.startFileUpload(proecesInstanceId).
    subscribe((res) => {
        console.log(res);
        this.formFieldsDto = res;
        this.changeFormFieldsDTO();
        this.processInstance = res.processInstanceId;
    }, error => {
      console.log(error);
    });
  }

  submitForm(value){
    console.log("FAJLOVI SUBMIT:");
    console.log(this.myFiles);
    for (let oneFileFiled of this.myFiles.values()) {
       console.log("Map Values= " +oneFileFiled);
       const formData = new FormData();
       for (var i = 0; i < oneFileFiled.length; i++) {
           formData.append("files", oneFileFiled[i]);
           this.filesService.upload(formData, this.formFieldsDto.taskId)
            .subscribe(response => {
                  console.log(response);
                  console.log('SUCCES');
                  alert("You upload files successfully!");
           }, error => {
                   console.log("Something gone wrong. File didn't upload!");
          });
       }
      }
  }

  changeFormFieldsDTO(){
    console.log("change input");
    let formFields = this.formFieldsDto.formFields;
    let copyFormFields = [];
    formFields.forEach(field => {
         let prop = field.properties;
         let temp = field;
         if(prop.hasOwnProperty('file')){
          temp.type.name = "file";
         }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
    console.log(this.formFields);
  }

  onFileChange(event){
    console.log("FILE ID"+ event.target.id);
    var files = [];
    for (var i = 0; i < event.target.files.length; i++) {
      files.push(event.target.files[i]);
    }
    this.myFiles.set(event.target.id, files);
  }

   resetForm(e: MouseEvent): void {
    e.preventDefault();
    this.validateForm.reset();
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsPristine();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    this.myFile = [];
    this.myFiles = new Map<string, string[]>();
  }


  
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



}
