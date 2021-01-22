import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NzButtonSize, NzMessageService } from 'ng-zorro-antd';
import { BookRequestService } from './../../services/book-request.service';
import { FilesService } from './../../services/files.service';

interface BookRequest {
  id: string;
  title: string;
  genres: any;
  writers: any;
  synopsis: string;
  isApproved: boolean;
}
@Component({
  selector: 'app-handwrite-upload',
  templateUrl: './handwrite-upload.component.html',
  styleUrls: ['./handwrite-upload.component.css']
})
export class HandwriteUploadComponent implements OnInit {
  isFileUploadEnabled: boolean = false;
  fileForm: FormGroup;
  formFieldsDto = null;
  bookRequest: BookRequest;
  buttonSize: NzButtonSize = 'large';

  myFiles = new Map<string, string[]>();

  constructor(private bookRequestService: BookRequestService,
              private filesService: FilesService,
              private message: NzMessageService) { }

  ngOnInit(): void {
    this.bookRequestService.getBookRequestFromProcess().subscribe(bookRequest => {
      console.log(bookRequest);
      this.bookRequest = bookRequest;
      if(bookRequest == null) {
        this.isFileUploadEnabled = false;
      } else {
        if(bookRequest.approved) {
          this.isFileUploadEnabled = true;
          this.setupFileForm();
        }
      }
    })
  }

  setupFileForm(): void {
    let processInstanceId = localStorage.getItem("publishBookProccessId");
    // console.log("PROCES FILE UPLOAD-A: " + processInstanceId);
    this.filesService.startFileUpload(processInstanceId).subscribe(res => {
        // console.log(res);
        this.formFieldsDto = res;
    }, error => {
      console.log(error);
    });
  }

  uploadFiles(){
    // console.log("FAJLOVI SUBMIT:");
    console.log(this.myFiles);
    for (let oneFileFiled of this.myFiles.values()) {
       console.log("Map Values= " +oneFileFiled);
       const formData = new FormData();
       for (var i = 0; i < oneFileFiled.length; i++) {
           formData.append("files", oneFileFiled[i]);
           this.filesService.upload(formData, this.formFieldsDto.taskId).subscribe(response => {
                  // console.log(response);
                  // console.log('SUCCES');
                  this.message.success("Your handwrite was successfuly uploaded!");
           }, error => {
             console.log("Something gone wrong. File didn't upload!");
          });
       }
      }
  }

  onFileChange(event){
    // console.log("FILE ID"+ event.target.id);
    var files = [];
    for (var i = 0; i < event.target.files.length; i++) {
      files.push(event.target.files[i]);
    }
    this.myFiles.set(event.target.id, files);
  }

}
