import { Component, Inject, ChangeDetectorRef, ChangeDetectionStrategy } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { PopUpComponent } from './popup.component';
import { Content } from '@angular/compiler/src/render3/r3_ast';
import { checkAndUpdateView } from '@angular/core/src/view/view';


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
  imageUrl = '';
  user;
  fbUser = '';

  constructor(private http: HttpClient, private chRef: ChangeDetectorRef) {
    
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

    //If the User Exists call handleUserExists ELSE call handleUserDoesNotExist
    this.http.get('http://localhost:8082/user/checkUser/'+fbUserId ).subscribe((res) => {
      console.log('inside' + res);
      this.user = res;
      this.imageUrl = res['profilePic'];
      this.fbUser = res['name']; 
    });
  }

  handleUserExists = () =>{

    this.showLogin = false;
    this.chRef.detectChanges();
  }

  handleUserDoesNotExist = () => {

  }

  onLoginClick() {
    console.log('clicked');
    

    if( !this.checkFB() ) return;
    this.showLogin = false;
    this.handleUser( 'samayu' , 'Saravanan Thangaraju' , 'https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=2365579596850855&height=50&width=50&ext=1537586649&hash=AeTAJmpQe29Pv45v' , 'info@samayusoftcorp.com'  );
    
    console.log(this.user);
    if (this.fbUser == null) {

    }
    }

    onBackClick() {
      this.showLogin = true;
      this.imageUrl = '';
      }
}


