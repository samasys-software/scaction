import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CastingCall } from '../types/castingcall';
import { User } from '../types/user';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-casting-coordinator',
  templateUrl: './casting-coordinator.component.html',
  styleUrls: ['./casting-coordinator.component.css']
})
export class CastingCoordinatorComponent implements OnInit {


public castingCalls: Observable<CastingCall[]>;

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    let localUserItem = localStorage.getItem('user');
    let user: User = JSON.parse(localUserItem);

    this.httpClient.get(environment.apiUrl + 'coordinator/castingcalls/' + user.userId ).subscribe((res) => {
      if (res != null) {


        let tmp: CastingCall[] = [];
        for (let key in res) {
          var cCall = new CastingCall(res[key]);
          tmp.push( cCall );
        }

        this.castingCalls = of(tmp).pipe();
      }
    },
    (error) => {
      this.castingCalls = null;
      // this.chRef.detectChanges();
    });


  }


}
