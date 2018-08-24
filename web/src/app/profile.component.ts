import { Component } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { PopUpComponent } from './popup.component';
import { Content } from '@angular/compiler/src/render3/r3_ast';


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

  constructor(private http: HttpClient) {

  }

  onLoginClick() {
    console.log('clicked');
    this.showLogin = false;

    this.http.get('http://localhost:8082/user/checkUser/samayu1').subscribe((res) => {
    console.log('inside' + res);
    this.user = res;
    this.imageUrl = res['profilePic'];
    this.fbUser = res['fbUser']; });
    console.log(this.user);
    if (this.fbUser == null) {

    }
    }

    onBackClick() {
      this.showLogin = true;
      this.imageUrl = '';
      }
}


