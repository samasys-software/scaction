import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CastingCall } from './types/castingcall';

@Injectable({
  providedIn: 'root'
})
export class CastingCallService {

  private messageSource = new BehaviorSubject<CastingCall>(null);
  currentMessage = this.messageSource.asObservable();

  changeMessage(message: CastingCall ) {
    this.messageSource.next( message );
  }


  constructor() { }
}
