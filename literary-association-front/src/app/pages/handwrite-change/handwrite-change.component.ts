import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BookRequestService } from './../../services/book-request.service';
import { FilesService } from './../../services/files.service';

@Component({
  selector: 'app-handwrite-change',
  templateUrl: './handwrite-change.component.html',
  styleUrls: ['./handwrite-change.component.css']
})
export class HandwriteChangeComponent implements OnInit {
  canChangeHandwrite: boolean = false;
  fileForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  inputValue = null;  
  myFile:string [] = [];
  myFiles = new Map<string, string[]>();

  constructor(private bookRequestService: BookRequestService, 
              private filesService: FilesService,
              private fb: FormBuilder) {   
    this.fileForm = this.fb.group({});
  }

  ngOnInit(): void {
    this.processInstance = localStorage.getItem("publishBookProccessId");
    this.bookRequestService.canChangeHandwrite(this.processInstance).subscribe(res => {
      console.log(res);
      this.canChangeHandwrite = res;
      if(res){
        this.getFormFields(this.processInstance);
      }
    })
  }

  getFormFields(processInstanceId): void {
    console.log("usla ovde");
    this.bookRequestService.getFormFields(processInstanceId).subscribe(res => {
      console.log(res);
      this.formFieldsDto = res;
      this.changeFormFieldsDTO();
      this.processInstance = res.processInstanceId;
    })
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
         }else if(prop.hasOwnProperty('textArea')){
           temp.type.name = "textArea";
           if(field.properties.nonEdit){
             this.inputValue = field.value.value;
           }
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


  uploadFiles(value){
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


}
