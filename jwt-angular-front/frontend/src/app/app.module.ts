import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import 'hammerjs';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { MatToolbarModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule,MatCheckboxModule } from  '@angular/material';
import { 
  MatCardModule, MatInputModule,MatFormFieldModule, MatProgressSpinnerModule,MatBadgeModule,MatSelectModule,MatRadioModule,
MatDatepickerModule,MatNativeDateModule,MatChipsModule,MatTooltipModule,MatTableModule,MatPaginatorModule} from  '@angular/material';
import {MatTabsModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { PmComponent } from './pm/pm.component';
import { AdminComponent } from './admin/admin.component';
import { FormsModule } from '@angular/forms';

import { httpInterceptorProviders } from './auth/auth-interceptor';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
 

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UserComponent,
    PmComponent,
    AdminComponent,
    NavBarComponent,
    MyAccountComponent,
    ForgotPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    HttpModule, FormsModule,
    HttpClientModule,
    HttpModule,
    MatDialogModule,
    AppRoutingModule,
    MatButtonModule,
    MatCheckboxModule,
    MatToolbarModule, 
    MatIconModule,
    MatBadgeModule,MatSelectModule,MatRadioModule,
    MatDatepickerModule,MatNativeDateModule,MatChipsModule,MatTooltipModule,MatTableModule,MatPaginatorModule,
    MatSlideToggleModule,MatGridListModule,
    MatTabsModule, MatCardModule, MatInputModule,MatFormFieldModule, MatProgressSpinnerModule
   
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
