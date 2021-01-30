import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { LiteraryAssociationService } from './../../services/literary-association.service';

@Component({
  selector: 'app-beta-reader-list',
  templateUrl: './beta-reader-list.component.html',
  styleUrls: ['./beta-reader-list.component.css']
})
export class BetaReaderListComponent implements OnInit {

  betaReaderForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  taskInstance = "";
  user = null;

  listOfOption: Array<{ label: string; value: string }> = []; //votes value
  multipleValue = []; //selected values of vote


  constructor(private router: Router,private literaryService: LiteraryAssociationService, private http: HttpClient, private fb: FormBuilder) {
      this.betaReaderForm = this.fb.group({});
  }


  ngOnInit(): void {
        let proecesInstanceId = localStorage.getItem("publishBookProccessId");
        this.user = JSON.parse(localStorage.getItem('user'));
        console.log("preuzimam listu beta readera: " + proecesInstanceId + this.user.username);
        this.literaryService.getBetaReadersFormField(proecesInstanceId).
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


  submitForm(value){
      console.log("submit");
      let o = new Array();
      let genreString = "";
      for(let i=0;i<this.multipleValue.length;i++){
             genreString += (this.multipleValue[i]);
             genreString += (";");
      }
      console.log(genreString);
      o.push({fieldId : "choosenBetaReaders", fieldValue : genreString});
      this.literaryService.sendChoosenBetaReaders(o,this.taskInstance)
            .subscribe(response => {
              console.log(response);
              console.log('BETAREADER CHOOSEN SUCCES');
              alert("You choosen betareaders successfully");
              this.multipleValue = [];
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
         if(prop.hasOwnProperty("enum")){
            this.createListForOption(field.defaultValue);
            temp.type.name = "enum";
            temp.type.values = this.listOfOption;
         }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
  }


  createListForOption(betaReadersString){
    const children: Array<{ label: string; value: string }> = [];
    let readers = betaReadersString.split(";");
    for (let i = 0; i < readers.length-1; i++) {
      let readerInfo = readers[i].split(":");
      children.push({ label: readerInfo[1] , value: readerInfo[0]});
    }
    this.listOfOption = children;
 }




}
