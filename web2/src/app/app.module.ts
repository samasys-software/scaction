import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule, ModalModule } from 'angular-bootstrap-md';
import { ProfileComponent } from './profile/profile.component';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MyHomeComponent } from './myhome/myhome.component';
import { RoleDisplayComponent } from './roledisplay/roledisplay.component';
import { CastingCoordinatorComponent } from './casting-coordinator/casting-coordinator.component';
import { CastingCallComponent } from './casting-call/casting-call.component';

const appRoutes: Routes = [
  { path: 'my-app-profile' , component:  ProfileComponent},
  { path: 'my-home' , component: MyHomeComponent },
  { path: 'casting-call', component: CastingCallComponent},
  { path: 'casting-coordinator', component: CastingCoordinatorComponent }];
  

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    MyHomeComponent,
    RoleDisplayComponent,
    CastingCoordinatorComponent,
    CastingCallComponent
  ],
  imports: [
    MDBBootstrapModule.forRoot(),
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    ModalModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: false}
    )
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [ NO_ERRORS_SCHEMA ]
})
export class AppModule { }
