import { Component, OnInit } from '@angular/core';
import { GetBookService } from '../../services/get-book.service';
import { UploadImageService } from '../../services/upload-image.service';
import { EditBookService } from '../../services/edit-book.service';
import { Book } from '../../models/book';
import {Params, ActivatedRoute, Router} from '@angular/router';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from "@angular/forms";

import * as moment from 'moment';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})
export class EditBookComponent implements OnInit {

  SERVER_URL = "http://localhost:8080/book/add/image";
  addBook: FormGroup; 
  private data: object; 

  private bookId: number;
  private editBook: Book = new Book();
  private bookUpdated: boolean;

  constructor(private fb: FormBuilder, private getBookService: GetBookService, private uploadImageService: UploadImageService, private editBookService: EditBookService, private router: Router,
                private route: ActivatedRoute) {this.initForm(); }

  onSubmit(){
    this.editBookService.updateBook(this.editBook).subscribe(
      res => {
        let data = JSON.parse(JSON.stringify(res));
        this.uploadImageService.modify(data.id);
        this.bookUpdated = true;
      }
    );
  }

  //this for the date for publication
  onDataChange(newdate) {
    const _ = moment();
    const date = moment(newdate);
    //this.date = date.toDate();
    console.log(date);
  }

  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.addBook.get('profile').setValue(file);
    }
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.bookId = Number.parseInt(params['id']); //get id from url /viewBook/:id - params (as string)and parse into int
    });
    
    this.getBookService.getBook(this.bookId).subscribe(
      (res : any[]) => {
        
        let data = JSON.parse(JSON.stringify(res));
        console.log(data);
        this.editBook = data;
      },
      error => {
        console.log(error);
      }
    );

  }

  initForm() {
    this.addBook = this.fb.group({
      title: ['', [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      author: [''],
      publisher: ['', [Validators.required, Validators.email, Validators.minLength(5)]],
      isbn: [''],
      shippingWeight: ['', Validators.maxLength(2)],
      pageNumber: [''],
      listPrice: [''],
      category: this.fb.array([]),
      ourPrice: [''],
      description: ['', Validators.maxLength(10)],
      format: this.fb.array([]),
      language: this.fb.array([])
    })
  }

}
