import { Component, OnInit, SecurityContext } from '@angular/core';
import { Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Observable, Observer } from 'rxjs';
import { ReaderService } from './../../services/reader.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-beta-reader-comment',
  templateUrl: './beta-reader-comment.component.html',
  styleUrls: ['./beta-reader-comment.component.css']
})
export class BetaReaderCommentComponent implements OnInit {
  
  betaCommentForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  taskInstance = "";
  user = null;

  inputValue = null;  //textArea


  constructor(private router: Router, private readerService: ReaderService, private http: HttpClient, private fb: FormBuilder) {
    this.betaCommentForm = this.fb.group({});
  }

  ngOnInit(): void {
    let proecesInstanceId = localStorage.getItem("processInstance");
    this.user = JSON.parse(localStorage.getItem('user'));
    console.log("PROCES KOMENTARISANJA-BETA READER: " + proecesInstanceId + this.user.username);
    this.readerService.startBetaReaderComment(proecesInstanceId, this.user.username).
    subscribe((res) => {
        console.log(res);
        this.formFieldsDto = res;
        this.changeFormFieldsDTO();
        this.processInstance = res.processInstanceId;
        this.taskInstance = res.taskId;
    }, error => {
      console.log(error);
    });

  }


  submitForm(value): void {
       console.log("submit betaReaderComment");
       console.log(this.inputValue);
       let o = new Array();
       o.push({fieldId : "comment", fieldValue : this.inputValue});
       this.user = JSON.parse(localStorage.getItem('user'));
       console.log(this.processInstance + this.user.username + this.taskInstance);
       this.readerService.sendBetaReaderComment(o,this.taskInstance,this.processInstance,this.user.username)
            .subscribe(response => {
              console.log(response);
              console.log('BETA READER COMMENTED SUCCES');
              alert("You commented successfully")
            }, error => {
              console.log("Error occured!");
          })



  }

  

  changeFormFieldsDTO(){
    let formFields = this.formFieldsDto.formFields;
    let copyFormFields = [];
    formFields.forEach(field => {
         let prop = field.properties;
         let temp = field;
         if(prop.hasOwnProperty('textArea')){
            temp.type.name = "textArea";
         }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
  }



}
