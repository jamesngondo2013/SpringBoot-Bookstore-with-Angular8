import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
 
import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';
import { SignUpInfo } from './signup-info';
import { ForgotPassword } from './forgot-password';
import {UpdateInfo} from './update-info';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/api/auth/signin';
  private signupUrl = 'http://localhost:8080/api/auth/signup';
  private forgotPasswordUrl = 'http://localhost:8080/api/auth/forgetPassword';
  private updateInfoUrl = 'http://localhost:8080/api/auth/updateInfo';
  private getCurrentUserUrl = 'http://localhost:8080/api/auth/getCurrentUser';
 
  constructor(private http: HttpClient) {
  }
 
  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }
 
  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }

  forgotPassword(info: ForgotPassword): Observable<string> {
    return this.http.post<string>(this.forgotPasswordUrl, info, httpOptions);
  }

  updateInfo(updateInfo: UpdateInfo): Observable<string> {
    return this.http.post<string>(this.updateInfoUrl, updateInfo, httpOptions);
  }

  getCurrentUser() {
    
    return this.http.get<string>(this.getCurrentUserUrl, httpOptions);
  }
}
