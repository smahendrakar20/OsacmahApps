import { CardComponent } from './Components/Card/Card.component';
import { FooterComponent } from './Components/Footer/Footer.component';
import { ListComponent } from './Components/List/List.component';
import { HeaderComponent } from './Components/header/header.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
@NgModule({
   declarations: [
      AppComponent,
      HeaderComponent,
      FooterComponent,
      ListComponent,
      CardComponent
   ],
   imports: [
      BrowserModule,
      AppRoutingModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
