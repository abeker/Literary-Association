import { HttpClient } from '@angular/common/http';
import { Component, OnInit, SecurityContext } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CommitteeService } from './../../services/committee.service';
declare var require: any
const FileSaver = require('file-saver');

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css']
})
export class VoteComponent implements OnInit {

  voteForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  taskInstance = "";
  user = null;

  listOfOption: Array<{ label: string; value: string }> = []; //votes value
  filesOption = []; //votes value
  multipleValue = []; //selected values of vote
  inputValue = null;  //textArea

  constructor(private router: Router, private committeeService: CommitteeService, private http: HttpClient, private fb: FormBuilder,  private sanitizer: DomSanitizer) {
    this.voteForm = this.fb.group({});
 }

  ngOnInit(): void {
    let proecesInstanceId = localStorage.getItem("processInstance");
    this.user = JSON.parse(localStorage.getItem('user'));
    console.log("PROCES GLASANJA ZA PISCA: " + proecesInstanceId + this.user.username);
    this.committeeService.startVote(proecesInstanceId, this.user.username).
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
    console.log(this.inputValue);
    console.log(this.multipleValue);
    let o = new Array();
    o.push({fieldId : "comment", fieldValue : this.inputValue});
    o.push({fieldId : "vote", fieldValue : this.multipleValue});

    this.user = JSON.parse(localStorage.getItem('user'));
    console.log(this.processInstance + this.user.username);
    this.committeeService.sendVote(o,this.taskInstance,this.processInstance,this.user.username)
            .subscribe(response => {
              console.log(response);
              console.log('VOTE SUCCES');
              alert("You voted successfully")
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
         }else if(field.type.name === "enum"){
            this.createListForOption(field.type.values);
            temp.type.name = "enum";
            temp.type.values = this.listOfOption;
        }else if(prop.hasOwnProperty('nonEditible')){
            temp.type.name = "nonEditibleFiles";
            this.createListFiles(field.defaultValue);
            temp.type.values = this.filesOption;
        }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
  }


  createListForOption(optionsString){
    const children: Array<{ label: string; value: string }> = [];
    for (var key in optionsString) {
      if (optionsString.hasOwnProperty(key)) {
          children.push({ label: key , value: key});
      }
  }
    this.listOfOption = children;
 }


  createListFiles(array){
    let genres = array.split(";");
    const children: Array<string> = [];
    for (let i = 0; i < genres.length-1; i++) {
      children.push(genres[i]);
    }
    this.filesOption = children;
  }


  downloadFile(fileName): void {
     console.log("klik na download" + fileName);
     this.committeeService.downloadFile(fileName).
     subscribe((res) => {
        this.showDownloadFile(res,fileName);
    }, error => {
      console.log(error);
    });
  }


   showDownloadFile(data: any, name:String) {
    const blob = new Blob([data], { type: 'application/octet-stream' });
    let fileUrl:string = this.sanitizer.sanitize(SecurityContext.RESOURCE_URL, this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob)));
    FileSaver.saveAs(fileUrl, name);
  }


}


