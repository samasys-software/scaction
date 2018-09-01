import { Component, OnInit } from '@angular/core';
import { User } from '../types/user';

@Component({
  selector: 'app-my-home',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
 public user: User;

  constructor() { }

  ngOnInit() {
   let userItem = localStorage.getItem('user');
   this.user = JSON.parse(userItem);
  }

}
