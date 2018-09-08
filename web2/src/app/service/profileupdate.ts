import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../types/user';


@Injectable({
  providedIn: 'root'
})
export class ProfileUpdateService {
  private messageSource = new BehaviorSubject<User>(null);
  currentMessage = this.messageSource.asObservable();

  changeMessage(message: User ) {
    this.messageSource.next( message );
  }

  constructor() {
    console.log('construction called');
   }



}