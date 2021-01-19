import { Component, OnInit } from '@angular/core';
import { WriterService } from './../../services/writer.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

    constructor(private writerService: WriterService) { }

    ngOnInit(): void {
    }

    publishBook(): void {
        this.writerService.publishStart().subscribe(response => {
            console.log(response);
        });
    }

}
