import { Component, OnInit } from '@angular/core';
import { GetBookService } from '../../services/get-book.service';
import { Book } from '../../models/book';
import {Params, ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-view-book',
  templateUrl: './view-book.component.html',
  styleUrls: ['./view-book.component.css']
})
export class ViewBookComponent implements OnInit {

  private book: Book = new Book();
  private bookId: number;

  constructor(private getBookService: GetBookService, private route: ActivatedRoute, private router: Router) { }

  onSelect(book: Book){
    this.router.navigate(['/editBook', this.book.id]);
  }

  ngOnInit() {

    this.route.params.forEach((params: Params) => {
      this.bookId = Number.parseInt(params['id']); //get id from url /viewBook/:id - params (as string)and parse into int
    }); 

    this.getBookService.getBook(this.bookId).subscribe(
      (res : any[]) => {
        
        let data = JSON.parse(JSON.stringify(res));
        console.log(data);
        this.book = data;
      },
      error => {
        console.log(error);
      }
    );
  }

}
