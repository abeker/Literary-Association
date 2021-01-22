import { Component, OnInit } from '@angular/core';
import { EditorService } from './../../../services/editor.service';
import { HandwriteService } from './../../../services/handwrite.service';

@Component({
  selector: 'app-plagiats',
  templateUrl: './plagiats.component.html',
  styleUrls: ['./plagiats.component.css']
})
export class PlagiatsComponent implements OnInit {
  isAlertMessageVisible: boolean = true;

  constructor(private editorService: EditorService,
              private handwriteService: HandwriteService) { }

  ngOnInit(): void {
    this.handwriteService.getHandwriteFromProcess().subscribe(handwrite => {
      console.log(handwrite);
      if(handwrite) {
        this.isAlertMessageVisible = false;
        this.editorService.getPlagiatForm().subscribe(response => {
          console.log(response);
        });
      }
    })
  }

}
