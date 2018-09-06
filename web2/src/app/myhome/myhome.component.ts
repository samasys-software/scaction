import { Component, OnInit } from '@angular/core';
import { User } from '../types/user';

@Component({
  selector: 'app-my-home',
  templateUrl: './myhome.component.html',
  styleUrls: ['./myhome.component.scss']
})
export class MyHomeComponent implements OnInit {
 public user: User;

  constructor() { }

  ngOnInit() {
   let userItem = localStorage.getItem('user');
   this.user = JSON.parse(userItem);
  }

}