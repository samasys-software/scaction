import { Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserHolderService } from './user-holder.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sca-action';
  constructor (private userHolder: UserHolderService) {

  }

}

