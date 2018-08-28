import { Component, Inject, ChangeDetectorRef, ChangeDetectionStrategy, Injectable, ViewChild } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from '../environments/environment';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { User } from './types/user';
import { Router, Routes } from '@angular/router';
import { UserHolderService } from './user-holder.service';

declare var window: any;
declare var location: any;
declare var FB: any;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})


export class ProfileComponent {
  showLogin = true;
  showPopUp = false;
  user: User;
  @ViewChild('content') private content;


  open() {

    const modalRef = this.modalService.open(this.content);
  }

  constructor(private http: HttpClient, private chRef: ChangeDetectorRef, private modalService: NgbModal,
    private router: Router, private userHolder: UserHolderService ) {

    (function(d, s, id){
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) {return;}
      js = d.createElement(s); js.id = id;
      js.src = '//connect.facebook.net/en_US/sdk.js';
      fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  window.fbAsyncInit = () => {
    console.log("fbasyncinit")

    FB.init({
        appId            : '461531394327909',
        autoLogAppEvents : true,
        xfbml            : true,
        version          : 'v2.10'
    });
  };

  }

  checkFB(){
    ///home/samayuadmin/Projects/stcamaction/scaction/web/dist

    if(window.location.href.indexOf('localhost')>-1 )          return true;

    FB.login( (data)=>this.handleFBResponse(data), {scope: 'public_profile,email'});
  }
  handleFBResponse = (response) => {
    console.log('handleFBReseponse='+response);
    if (response.authResponse) {
        console.log('Welcome!  Fetching your information.... ');
          FB.api('/me', {fields: 'id,name,email,picture'}, (data) => this.handleSuccessResponse(data) );
        } else {
        console.log('User cancelled login or did not fully authorize.');
        }
  }

  handleSuccessResponse = (meResponse) => {
    console.log('handleSuccessResponse='+meResponse);
    this.handleUser(meResponse.id , meResponse.name, meResponse.picture.data.url , meResponse.email );

  }

  /*
   Write code in handleUser to go to AJAX Call and Fetch the User
  */
  handleUser = (fbUserId, fbName , fbImageUrl , fbEmail ) => {

    this.http.get(environment.apiUrl + 'user/checkUser/'+ fbUserId ).subscribe((res) => {
      if (res != null) {
        this.handleUserExists(res);
      }
      else {
        this.showLogin = true;
        this.handleUserDoesNotExist(fbUserId, fbName,  fbEmail, fbImageUrl);
      }
    console.log('inside' + res);
    },
  (error) => {
    this.handleUserDoesNotExist(fbUserId, fbName,  fbEmail, fbImageUrl);
  });
  }


  handleUserExists = (res: any ) =>{
    this.user = new User(res);
    this.showLogin = false;
    this.chRef.detectChanges();
  }

  message: string;

  handleUserDoesNotExist = (fbUserId, fbName, fbEmail, fbProfilePic) => {
    //var popUpComponent = new PopUpComponent(this.modalService);
    this.modalService.open(this.content, {ariaLabelledBy: 'modal-basic-title'});

    this.userHolder.changeMessage({ fbUserId : fbUserId , fbName : fbName , fbEmail : fbEmail , fbProfilePic : fbProfilePic });
    //this.router.navigate(['/my-profile-update']);

  }

  onLoginClick() {
    console.log('clicked');


    if( !this.checkFB() ) return;

    this.handleUser( 'samayu1' , 'Saravanan Thangaraju' , 'https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=2365579596850855&height=50&width=50&ext=1537586649&hash=AeTAJmpQe29Pv45v' , 'info@samayusoftcorp.com'  );

    console.log(this.user);
    }

    yesClick() {
      console.log('yesclicked');
      this.router.navigate(['/my-profile-update']);
    }

}


