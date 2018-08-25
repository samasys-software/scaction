import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ProfileComponent } from './profile.component';
import { PopUpComponent } from './popup.component';
import { PopupContent } from './popup.component';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule, Routes } from '@angular/router';
import { MyProfileUpdateComponent } from './my-profile-update';

const appRoutes: Routes = [
  { path: 'my-profile-update', component: MyProfileUpdateComponent }];

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    PopUpComponent,
    PopupContent,
    MyProfileUpdateComponent
   ],
  imports: [
    BrowserModule,
    HttpClientModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
