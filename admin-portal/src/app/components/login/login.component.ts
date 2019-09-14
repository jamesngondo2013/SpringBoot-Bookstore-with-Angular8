import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../services/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private credential = {'username':'', 'password' : ''};
  private loggedIn = false;

  constructor(private loginService: LoginService) { }

  onSubmit() {
  	this.loginService.sendCredential(this.credential.username, this.credential.password).subscribe(
		  (res: {token: string}) => {
			console.log(res.token);
			localStorage.setItem('xAuthToken', res.token);
			this.loggedIn = true;
			console.log("User is logged in");
			location.reload();

		},
		error => {
			console.log(error);
		}
	  );

	} 

  ngOnInit() {
  	this.loginService.checkSession().subscribe(
		(res: {token: string}) => {
			console.log(res.token); 
			this.loggedIn = true;
		},
		error => {
			this.loggedIn=false;
		}
	  );

  }

}
