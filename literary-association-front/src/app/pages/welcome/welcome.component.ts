import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private message: NzMessageService) { }

  ngOnInit(): void {
  }

  purchase(): void {
    this.message.info("Successfuly purchased!");
    window.open('localhost:3000', "_blank");
  }

}
