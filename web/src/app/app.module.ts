import { BrowserModule } from '@angular/platform-browser';
import { NgModule,  CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { AppComponent } from './app.component';
import { ProfileComponent } from './profile.component';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule, Routes } from '@angular/router';
import { MyProfileUpdateComponent } from './my-profile-update.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomepageComponent } from './homepage/homepage.component';
import { RoleDisplayComponent } from './role-display/role-display.component';
import { CastingCallComponent } from './casting-call/casting-call.component';
import { CastingCoordinatorComponent } from './casting-coordinator/casting-coordinator.component';




const appRoutes: Routes = [
  { path: 'my-profile-update', component: MyProfileUpdateComponent },
 { path: 'my-home', component: HomepageComponent },
 { path: 'casting-call', component: CastingCallComponent},
{ path: 'casting-coordinator', component: CastingCoordinatorComponent }];


@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    MyProfileUpdateComponent,
    HomepageComponent,
    RoleDisplayComponent,
    CastingCallComponent,
    CastingCoordinatorComponent
    ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: false}
    ),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
