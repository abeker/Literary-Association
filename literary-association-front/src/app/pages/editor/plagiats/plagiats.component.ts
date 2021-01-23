import { Component, OnInit, SecurityContext } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { NzMessageService } from 'ng-zorro-antd';
import { CommitteeService } from 'src/app/services/committee.service';
import { EditorService } from './../../../services/editor.service';
import { HandwriteService } from './../../../services/handwrite.service';
declare var require: any
const FileSaver = require('file-saver');

interface FieldDto {
  fieldId: string;
  fieldValue: any;
}
@Component({
  selector: 'app-plagiats',
  templateUrl: './plagiats.component.html',
  styleUrls: ['./plagiats.component.css']
})
export class PlagiatsComponent implements OnInit {
  isAlertMessageVisible: boolean = true;
  voteForm: FormGroup;
  formFieldsDto = null;
  formFields = [];
  processInstance = "";
  taskInstance = "";
  filesOption = []; //votes value
  isPlagiat: boolean = false;
  submitedFormDto: FieldDto[] = [];

  isModalVisible = false;
  isModalOkLoading = false;
  isModalBetaReadersVisible = false;
  isModalBetaReadersOkLoading = false;
  textareaText?: string;
  sendToBetaReaders: boolean = false;

  constructor(private editorService: EditorService,
              private handwriteService: HandwriteService,
              private sanitizer: DomSanitizer,
              private fb: FormBuilder,
              private committeeService: CommitteeService,
              private message: NzMessageService) {
  this.voteForm = this.fb.group({});
 }

  ngOnInit(): void {
    this.handwriteService.getHandwriteFromProcess().subscribe(handwrite => {
      console.log(handwrite);
      if(handwrite) {
        this.isAlertMessageVisible = false;
        this.editorService.getPlagiatForm().subscribe(response => {
          console.log(response);
          if(!response) {
            this.isAlertMessageVisible = true;
          }
          this.formFieldsDto = response;
          this.changeFormFieldsDTO();
          this.processInstance = response.processInstanceId;
          this.taskInstance = response.taskId;
          this.addFormControls();
          this.initializeSubmitDto();
        });
      }
    })
  }

  initializeSubmitDto(): void {
    this.formFieldsDto.formFields.forEach(field => {
      if(field.id === 'writer') {
        this.submitedFormDto.push({
          fieldId: field.id,
          fieldValue: field.properties.writer
        });
      } else if(field.id === 'isPlagiat') {
        this.submitedFormDto.push({
          fieldId: field.id,
          fieldValue: this.isPlagiat
        });
      } else {
        this.submitedFormDto.push({
          fieldId: field.id,
          fieldValue: field.defaultValue
        });
      }
    });
  }

  addFormControls(): void {
    this.formFieldsDto.formFields.forEach(field => {
      if(field.id === 'writer') {
        this.voteForm.addControl(field.id,
          new FormControl({value: field.properties.writer, disabled: true}, []));
      } else if(field.id === 'genres') {
        this.voteForm.addControl(field.id,
          new FormControl({value: this.processGenres(field.defaultValue), disabled: true}, []));
      } else if(field.id === 'isPlagiat') {
        this.voteForm.addControl(field.id,
          new FormControl({value: field.defaultValue}, []));
      } else if(field.id !== 'nonEditibleFiles') {
        this.voteForm.addControl(field.id,
          new FormControl({value: field.defaultValue, disabled: true}, []));
      }
    });
  }

  processGenres(genres): void {
    let result = genres.replace(";", ", ");
    return result.substring(0, result.length-1);
  }

  changeFormFieldsDTO(){
    let formFields = this.formFieldsDto.formFields;
    let copyFormFields = [];
    formFields.forEach(field => {
         let prop = field.properties;
         let temp = field;
         if(prop.hasOwnProperty('nonEditible')){
            temp.type.name = "nonEditibleFiles";
            this.createListFiles(field.defaultValue);
            temp.type.values = this.filesOption;
        }
         copyFormFields.push(temp);
    });
    this.formFields = copyFormFields;
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
    //  console.log("klik na download" + fileName);
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

  submitForm(){
    console.log("submit");
    for (const key in this.voteForm.controls) {
      this.voteForm.controls[key].markAsDirty();
      this.voteForm.controls[key].updateValueAndValidity();
    }
    this.submitedFormDto.forEach(field => {
      if(field.fieldId === "isPlagiat") {
        field.fieldValue = this.isPlagiat
      }
    });
    console.log(this.submitedFormDto);

    if(!this.isPlagiat) {
      this.isModalBetaReadersVisible = true;
    } else {
      this.isModalVisible = true;
    }
  }

  handleOk(): void {
    this.isModalOkLoading = true;
    this.editorService.sendCheckPlagiarism(this.submitedFormDto, this.textareaText, false).subscribe(response => {
      console.log(response);
      this.message.success('Successfuly submitted!');
    })
    setTimeout(() => {
      this.isModalVisible = false;
      this.isModalOkLoading = false;
    }, 1000);
  }

  handleCancel(): void {
    this.isModalVisible = false;
  }

  handleOkBetaReaders(): void {
    this.isModalBetaReadersOkLoading = true;
    this.editorService.sendCheckPlagiarism(this.submitedFormDto, "no_reason", this.sendToBetaReaders).subscribe(response => {
      console.log(response);
      this.message.success('Successfuly submitted!');
    })
    setTimeout(() => {
      this.isModalBetaReadersVisible = false;
      this.isModalBetaReadersOkLoading = false;
    }, 1000);
  }

  handleCancelBetaReaders(): void {
    this.isModalBetaReadersVisible = false;
  }

}
