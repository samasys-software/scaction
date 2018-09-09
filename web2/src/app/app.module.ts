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
import { CastingCallSearchComponent } from './casting-call-search/casting-call-search.component';
import { CastingCallDetailComponent } from './casting-call-detail/casting-call-detail.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NotificationsComponent } from './notifications/notifications.component';

const appRoutes: Routes = [
  { path: 'app-profile' , component:  ProfileComponent},
  { path: 'my-home' , component: MyHomeComponent },
  { path: 'casting-call', component: CastingCallComponent},
  { path: 'casting-coordinator', component: CastingCoordinatorComponent },
  { path: 'casting-call-search' , component: CastingCallSearchComponent },
  { path: 'casting-call-details' , component: CastingCallDetailComponent },
  { path: 'notifications' , component: NotificationsComponent },
  { path: '' , component: HomePageComponent}]
  

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    MyHomeComponent,
    RoleDisplayComponent,
    CastingCoordinatorComponent,
    CastingCallComponent,
    CastingCallSearchComponent,
    CastingCallDetailComponent,
    HomePageComponent,
    NotificationsComponent
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
