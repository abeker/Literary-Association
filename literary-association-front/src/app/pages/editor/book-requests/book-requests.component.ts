import { Component, OnInit } from '@angular/core';
import { BookRequestService } from './../../../services/book-request.service';
import { EditorService } from './../../../services/editor.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';

interface BookRequest {
  id: string;
  title: string;
  genres: any;
  writers: any;
  synopsis: string;
  isApproved: boolean;
}
interface FieldDto {
  fieldId: string;
  fieldValue: any;
}
@Component({
  selector: 'app-book-requests',
  templateUrl: './book-requests.component.html',
  styleUrls: ['./book-requests.component.css']
})
export class BookRequestsComponent implements OnInit {
  listOfData: BookRequest[] = [];
  isFormVisible: boolean = false;
  formFieldsDto: any = null;
  isApproved: boolean = false;
  validateForm: FormGroup;
  submitedFormDto: FieldDto[] = [];
  inputValue?: string;

  isModalVisible = false;
  isModalOkLoading = false;
  textareaText?: string;

  constructor(private bookRequestService: BookRequestService,
              private editorService: EditorService,
              private fb: FormBuilder,
              private message: NzMessageService) {
    this.validateForm = this.fb.group({});
  }

  ngOnInit(): void {
    this.bookRequestService.getAll().subscribe(response => {
      console.log(response);
      this.listOfData = response;
      if(response === undefined || response.length == 0) {
        this.listOfData.push({
          id: 'd9be1ed0-edef-481b-ad4c-4aedc91e0be4',
          title: 'The Great Idea',
          genres: ['thriller', 'drama'],
          writers: ['Writer Writer'],
          synopsis: 'This is great idea',
          isApproved: true
        });
      }
    });
  }

  listToString(list): string {
    let retString: string = "";
    list.forEach(element => {
      retString += element + ", ";
    });
    retString = retString.substring(0, retString.length-2);
    return retString;
  }

  proccessLastOne(): void {
    this.editorService.processLastOne().subscribe(response => {
      this.isFormVisible = true;
      this.formFieldsDto = response.formFields;
      this.initializeSubmitDto();
      console.log(this.formFieldsDto);
      this.formFieldsDto.forEach(field => {
        if(field.type.name !== 'boolean') {
          this.validateForm.addControl(field.id,
            new FormControl({value: field.properties.value, disabled: true}, []));
        } else {
          this.validateForm.addControl(field.id,
            new FormControl({value: field.properties.value}, []));
        }

      });
    });
  }

  initializeSubmitDto(): void {
    this.formFieldsDto.forEach(field => {
      this.submitedFormDto.push({
        fieldId: field.id,
        fieldValue: field.properties ? field.properties.value : false
      });
    });
  }

  tableViewSwitch(): void {
    this.isFormVisible = false;
  }

  submitForm(): void {
    for (const key in this.validateForm.controls) {
      this.validateForm.controls[key].markAsDirty();
      this.validateForm.controls[key].updateValueAndValidity();
    }
    this.submitedFormDto.forEach(field => {
      if(field.fieldId === "isApproved") {
        field.fieldValue = this.isApproved
      }
    });

    if(this.isApproved) {
      this.editorService.sendApprovement(this.submitedFormDto, "no_reason").subscribe(response => {
        console.log(response);
        this.message.success('Successfuly submitted!');
      })
    } else {
      this.isModalVisible = true;
    }

  }

  handleOk(): void {
    this.isModalOkLoading = true;

    this.submitedFormDto.push({
      fieldId: "reason",
      fieldValue: this.textareaText
    })
    this.editorService.sendApprovement(this.submitedFormDto, this.textareaText).subscribe(response => {
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

}
