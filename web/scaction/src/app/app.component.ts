import { Component } from '@angular/core';
declare var window: any;
declare var FB: any;
declare var document: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Start Camera, Action!';
  public facebookLoggedIn: boolean;
  public facebookName : string;
  public facebookProfile : string ;


constructor() {
this.facebookLoggedIn = true;
this.facebookName = "Test";
  this.facebookProfile = "https://i.pinimg.com/originals/50/a8/f5/50a8f5672410164d02029418fda5c5f7.jpg";
 var b = true;
 if( b) return;
// This function initializes the FB variable
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

       FB.login(function(response) {
         console.log(response);
         if (response.authResponse) {
             console.log('Welcome!  Fetching your information.... ');
             FB.api('/me', {fields: 'id,name,email,picture'}, function(response) {
               this.facebookLoggedIn = false;
               this.facebookName = response.name;
                this.facebookProfile = response.picture.data.url;
               console.log('Good to see you, ' + response.name + '.');
               console.log('Profile Pic , ' + response.id + '.');
               console.log('Profile Pic , ' + response.email + '.');
               console.log('Profile Pic , ' + response.picture.data.url + '.');
              console.log('http://graph.facebook.com/' + response.id + '/picture?type=normal');
              var bikeImage = document.getElementById("image-holder") as HTMLImageElement;
              bikeImage.src =  response.picture.data.url;
             });
            } else {
             console.log('User cancelled login or did not fully authorize.');
            }
       }, {scope: 'public_profile,email'});

        // This is where we do most of our code dealing with the FB variable like adding an observer to check when the user signs in

// ** ADD CODE TO NEXT STEP HERE **
    };
  }

  loginClick(): void {
      console.log("Test");
  }


}
