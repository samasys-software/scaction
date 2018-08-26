import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserHolderService {
  public fbUserId: any;
  public fbName: any;
  public fbEmail: any;
  public fbProfilePic: any;

  constructor() {
    console.log('construction called');
   }
}
