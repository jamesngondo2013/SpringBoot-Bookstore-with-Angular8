import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  info: any;
  isLoggedIn = false;
 
  constructor(private token: TokenStorageService) { }
 
  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
  }
 
  logout() {
    this.token.signOut();
    this.isLoggedIn = false;
    window.location.reload();
  }

}
