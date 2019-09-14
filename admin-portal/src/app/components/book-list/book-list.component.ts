import { Component, OnInit, Inject } from '@angular/core';
import { Book } from '../../models/book';
import {Router} from '@angular/router';
import { GetBookListServiceService } from '../../services/get-book-list-service.service';
import {RemoveBookService} from '../../services/remove-book.service';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { from } from 'rxjs';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  private selectedBook : Book;
  private checked : boolean;
  private bookList : Book[];
  private allChecked : boolean;
  private removeBookList : Book[] = new Array();
  private bookId: number;
  private book: Book = new Book();

  constructor(private getBookListService: GetBookListServiceService, private removeBookService: RemoveBookService, private router: Router, public dialog: MatDialog) { }

  //click on book title or view details button will navigate to a view book page based on selected bookId
  onSelect(book: Book){
    this.selectedBook = book;
    this.router.navigate(['/viewBook', this.selectedBook.id]);
  }

  openDialog(book: Book){
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '300px'   
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if(result=="yes"){
        this.removeBookService.sendBook(book.id).subscribe(
          res => {
            let data = JSON.parse(JSON.stringify(res));
            console.log(res);
            location.reload();
          },
          error => {
            console.log(error);
          }
        );
      }
    });
  }

   //click on book title or 'Edit' - button will navigate to a edit book page based on selected bookId
  onSelectUpdateFromListBook(book: Book){
    this.selectedBook = book;
    this.router.navigate(['/editBook', this.selectedBook.id]);
  }

  ngOnInit() {

    this.getBookListService.getBookList().subscribe(
      (res : any[]) => {
        console.log(res);
        this.bookList = res;
      },
      error => {
        console.log(error);
        
      }     

    );

  }

}


@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: './dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {

  constructor(public dialogRef: MatDialogRef<DialogOverviewExampleDialog>){}
   
  onNoClick(): void {
    this.dialogRef.close();
  }

}

