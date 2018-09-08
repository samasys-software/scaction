import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserHolderService {

  private messageSource = new BehaviorSubject<object>(null);
  currentMessage = this.messageSource.asObservable();

  changeMessage(message: object ) {
    this.messageSource.next( message );
  }

  constructor() {
    console.log('construction called');
   }
}