import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import {AddBookService} from '../../services/add-book.service';
import {UploadImageService} from '../../services/upload-image.service';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from "@angular/forms";
import {Router} from '@angular/router';

import * as moment from 'moment';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {

  SERVER_URL = "http://localhost:8080/book/add/image";
  addBook: FormGroup; 
  private data: object; 

  private newBook: Book = new Book();
  private bookAdded: boolean;	

  constructor(private fb: FormBuilder, private addBookService:AddBookService, private uploadImageService:UploadImageService,
     private router:Router) {this.initForm(); }

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

  onSubmit() {
  	this.addBookService.sendBook(this.newBook).subscribe(
  		res => { 
        
        let data = JSON.parse(JSON.stringify(res));
        console.log("image data id from res=>: "+ data.id);
        this.uploadImageService.upload(data.id);
        this.bookAdded=true;
        //ready for new book after successfully added one
  			this.newBook = new Book();
  			this.newBook.active=true;
  			this.newBook.category="Management";
  			this.newBook.language="English";
  			this.newBook.format="Paperback";
  		},
  		error => {
  			console.log(error);
  		}
    );
    
    this.router.navigate(['/add-book']);
  }

  initForm() {
    this.addBook = this.fb.group({
      title: ['', [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      author: ['', [Validators.required, Validators.minLength(2)]],
      publisher: ['', [Validators.required, Validators.minLength(2)]],
      isbn: ['', [Validators.required, Validators.minLength(5)]],
      shippingWeight: ['', Validators.maxLength(2)],
      pageNumber: [''],
      listPrice: [''],
      category: this.fb.array([]),
      ourPrice: [''],
      description: ['', Validators.maxLength(256)],
      format: this.fb.array([]),
      language: this.fb.array([]),
    
    })
  }

  onRegister() {  
    console.log("Form Value", this.addBook.value);
  }

  ngOnInit() {

    this.bookAdded=false;
  	this.newBook.active=true;
  	this.newBook.category="Management";
  	this.newBook.language="English";
    this.newBook.format="Paperback";
    
  }

}
