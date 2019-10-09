import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import {Router} from '@angular/router';
import {UpdateInfo} from '../auth/update-info';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {

  form: any = {};
  updateInfo: UpdateInfo;

  private dataFetched = false;
	private loginError: boolean;
	private loggedIn: boolean;

	private updateSuccess: boolean;
	private incorrectPassword: boolean;
  private emailSent: boolean =false;

  constructor(private tokenStorage: TokenStorageService, private authService: AuthService, private router: Router) { }

  getCurrentUser() {
  	this.authService.getCurrentUser().subscribe(
  		res => {
        console.log(res);
        this.updateInfo = JSON.parse(JSON.stringify(res));
        console.log("Inside getCurrentUser()");
        console.log(this.updateInfo.firstname);
  			this.dataFetched = true;
  		},
  		err => {
  			console.log(err);
  		}
  	);
  }

  onUpdateUserInfo() {
    console.log("form: "+this.form);

    this.emailSent = false;

    this.updateInfo = new UpdateInfo(
      this.form.id,
      this.form.firstname,
      this.form.lastname,
      this.form.username,
      this.form.email,
      this.form.password,
      this.form.newPassword);
 
    this.authService.updateInfo(this.updateInfo).subscribe(
      data => {
        console.log(data);
        this.updateSuccess=true;
        this.emailSent = true;
        
      },
      error => {
        console.log(error);
        let errorMessage = error.text();
  			if(errorMessage==="Incorrect current password!") this.incorrectPassword=true;
       
      }
    );
  }

  logout(){

    this.tokenStorage.signOut();
    this.loggedIn=false;
    window.location.reload();
    this.router.navigate(['/']);
   
  }

  ngOnInit() {

    if (this.tokenStorage.getToken()) {
     this.loggedIn = true; 
    }

    this.getCurrentUser();

  }

}
