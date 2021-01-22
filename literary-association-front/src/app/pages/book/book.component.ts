import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { WriterService } from './../../services/writer.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
    formFieldsDto: any = null;
    validateForm: FormGroup;
    multipleValue = []; //selected values of genre
    listOfOption: any = null;
    formFields = [];

    constructor(private writerService: WriterService,
                private fb: FormBuilder,
                private message: NzMessageService) {
      this.validateForm = this.fb.group({});
   }

    ngOnInit(): void {
      this.publishBook();
    }

    publishBook(): void {
        this.writerService.publishStart().subscribe(response => {
          localStorage.setItem('publishBookProccessId', response.processInstanceId);
          // console.log(response.formFieldsDto.formFields);
          this.formFieldsDto = response.formFieldsDto.formFields;
          this.formFieldsDto.forEach( (field) =>{
            this.changeFormFieldsDTO();
            let field_validations = field.validationConstraints;
            let validators = this.newValidationRule(field_validations, field);
            this.validateForm.addControl(field.id, this.fb.control('', validators));
          });
        });
    }

    submitForm(value): void {
        for (const key in this.validateForm.controls) {
          this.validateForm.controls[key].markAsDirty();
          this.validateForm.controls[key].updateValueAndValidity();
        }

        let map = new Array();
        for (var property in value) {
          if (property === "genre" || property === "genres"){
              let genreString = "";
              for(let i=0;i<this.multipleValue.length;i++){
                    genreString += (this.multipleValue[i]);
                    genreString += (";");
              }
              // console.log(genreString);
              map.push({fieldId : property, fieldValue : genreString});
          }else{
            map.push({fieldId : property, fieldValue : value[property]});
          }
        }
        // console.log(map);

        this.writerService.submitPublishForm(map).subscribe(response => {
          this.message.success('Successfuly submited!');
        }, () => {
          this.message.error('Something went wrong.');
        });
    }

    changeFormFieldsDTO(){
      let copyFormFields = [];
      this.formFieldsDto.forEach(field => {
           let prop = field.properties;
           let temp = field;
           if(prop.hasOwnProperty('enum')){
              this.createListForOption(field.properties.values);
              temp.type.name = "enum";
              console.log(this.listOfOption);

              temp.type.values = this.listOfOption;
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
      field_validations.forEach(element => {
        switch(element.name) {
          case "required":
            validators.push(Validators.required);
            break;
          case "minlength":
            validators.push(Validators.minLength(element.configuration));
            break;
          case 'maxlength':
            validators.push(Validators.maxLength(element.configuration));
            break;
        }
      });

      return validators;
    }

}
