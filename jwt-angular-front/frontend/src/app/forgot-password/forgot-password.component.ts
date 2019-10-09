import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ForgotPassword } from '../auth/forgot-password';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  form: any = {};
  forgotPassword: ForgotPassword;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';
  emailSent: boolean =false;

  constructor(private authService: AuthService) { }

  onSubmit() {
    console.log(this.form);

    this.emailSent = false;

    this.forgotPassword = new ForgotPassword(
     
      this.form.email);
 
    this.authService.forgotPassword(this.forgotPassword).subscribe(
      data => {
        console.log(data);
        this.emailSent = true;
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  ngOnInit() {
  }

}
