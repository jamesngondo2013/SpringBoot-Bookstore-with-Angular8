import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetBookListServiceService {

  constructor(private http: HttpClient) { }

  getBookList() {
  	let url = "http://localhost:8080/book/bookList";
    
    let headers = new HttpHeaders ({
      'Content-Type': 'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });

    return this.http.get(url, {headers: headers});
  }

}
