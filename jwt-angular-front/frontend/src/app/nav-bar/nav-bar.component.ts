import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private isloggedIn = false;
  roles: string[] = [];
  username: string;
  welcome: string;
  name: string;

  constructor(private tokenStorage: TokenStorageService, private router:Router) { }

  logout(){

    this.tokenStorage.signOut();
    this.isloggedIn=false;
    this.reloadPage();
    this.router.navigate(['/']);
   
  }

  reloadPage() {
    window.location.reload();
  }

  ngOnInit() {

    if (this.tokenStorage.getToken()) {
      this.isloggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
      this.username = this.tokenStorage.getUsername();
      this.welcome = "  Welcome ";
    }
  }

}
