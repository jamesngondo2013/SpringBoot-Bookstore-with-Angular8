import { Injectable } from '@angular/core';
import { from } from 'rxjs';
import {HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GetBookService {

  constructor(private http: HttpClient) { }

  getBook(id: number) {
  	let url = "http://localhost:8080/book/"+id;
    
    let headers = new HttpHeaders ({
      'Content-Type': 'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });

    return this.http.get(url, {headers: headers});
  }
}
