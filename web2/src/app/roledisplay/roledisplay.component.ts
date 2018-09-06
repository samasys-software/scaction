import { Component, OnInit } from '@angular/core';
import { User } from '../types/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-role-display',
  templateUrl: './roledisplay.component.html',
  styleUrls: ['./roledisplay.component.scss']
})
export class RoleDisplayComponent implements OnInit {

  public user: User;
  roleTypes: any[];
  public names: Array<any>[];


  constructor(private router: Router) { }

  ngOnInit() {
    let localUserItem = localStorage.getItem('user');

  if (localUserItem != null) {
    this.user = JSON.parse(localUserItem);
    this.roleTypes = this.user.userRoles;
  }
  }

}

