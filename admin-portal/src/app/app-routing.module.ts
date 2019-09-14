import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddBookComponent } from './components/add-book/add-book.component';
import { BookComponent } from './components/book/book.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { ViewBookComponent } from './components/view-book/view-book.component';
import {EditBookComponent} from './components/edit-book/edit-book.component';


const routes: Routes = [
  {
    path:'',
    redirectTo: '/login',
    pathMatch:'full'
  },
  { 
    path:'home', 
    component: HomeComponent
  },
  { 
    path:'allbooks', 
    component: BookListComponent
  },
  { 
    path:'add-book', 
    component: AddBookComponent
  },
  { 
    path:'login', 
    component: LoginComponent
  },
  { 
    path:'register', 
    component: RegisterComponent
  },
  { 
    path:'viewBook/:id', 
    component: ViewBookComponent
  },
  { 
    path:'editBook/:id', 
    component: EditBookComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
