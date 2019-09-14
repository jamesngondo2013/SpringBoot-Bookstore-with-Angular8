import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})

export class NavBarComponent implements OnInit {

  private loggedIn = false;

  constructor(private loginService: LoginService, private router:Router) { }

  toggle(){
    this.loggedIn = !this.loggedIn;
  }

  logout() {
    this.loginService.logout().subscribe(
      (res: {token: string}) => {
        location.reload();    
      },
      error => {
        console.log(error);
      }
    );

    this.router.navigate(['/']);
  }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      (res: {token: string}) =>{
       // console.log(res);
        this.loggedIn=true;
      },
      error => {
        this.loggedIn=false;
      }
    );
  }

}
