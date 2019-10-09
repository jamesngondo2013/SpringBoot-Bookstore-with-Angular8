import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { UserComponent } from './user/user.component';
import { PmComponent } from './pm/pm.component';
import { AdminComponent } from './admin/admin.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';


const routes: Routes = [
  {
    path:'',
    redirectTo: '/home',
    pathMatch:'full'
  },
  { 
    path:'home', 
    component: HomeComponent
  },
  { 
    path:'register', 
    component: RegisterComponent
  },
  { 
    path:'login', 
    component: LoginComponent
  },
  {
    path: 'my-account',
    component: MyAccountComponent
  },
  {
    path: 'admin',
    component: AdminComponent
  }
  ,
  {
    path: 'manager',
    component: PmComponent
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
