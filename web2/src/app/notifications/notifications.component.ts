import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { User } from '../types/user';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {

  user: User;
  notifications: Observable<Notification[]>;

  ngOnInit() {
    let userItem = localStorage.getItem('user'); 
    if (userItem != null) {
      this.user = JSON.parse(userItem);

      //Fetch Notifications

      this.http.get( environment.apiUrl + 'user/notifications/'+ this.user.userId ).subscribe((res) => {
        if (res != null) {
          const pTmp: Notification[] = [];
          for( let key in res ){
              let notification:Notification = res[key];
              pTmp.push(notification);
          }

          this.notifications = of(pTmp);
        }
        else {
          console.log("Notification Response null");
          this.notifications = null;
        }
      
      },
    (error) => {
      console.log( error );
      this.notifications = null;
    });

   }
  }

   constructor(private http:HttpClient){

   }
  }
