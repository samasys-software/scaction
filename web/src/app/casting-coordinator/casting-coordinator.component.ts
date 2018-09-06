import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CastingCall } from '../types/castingcall';
import { User } from '../types/user';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CastingCallService } from '../casting-call.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-casting-coordinator',
  templateUrl: './casting-coordinator.component.html',
  styleUrls: ['./casting-coordinator.component.css']
})
export class CastingCoordinatorComponent implements OnInit {
  public castingCalls: Observable<CastingCall[]>;
  private tempCastingCall: CastingCall;

  constructor(private httpClient: HttpClient, private router: Router) {}

  onEditCastingCalls(call: any) {
    console.log(call);
    localStorage.setItem('castingCall', JSON.stringify(call));
    this.router.navigate(['/casting-call']);
  }

  onEditCastingCall(value: number) {
    this.castingCalls.subscribe(data => {
      this.tempCastingCall = data.filter(castingCall => {
        return castingCall.id === value;
      })['0'];
    });
    localStorage.setItem('castingCall', JSON.stringify(this.tempCastingCall));
  }

  ngOnInit() {
    const localUserItem = localStorage.getItem('user');
    const user: User = JSON.parse(localUserItem);

    this.httpClient
      .get(environment.apiUrl + 'coordinator/castingcalls/' + user.userId)
      .subscribe(
        res => {
          if (res != null) {
            const tmp: CastingCall[] = [];
            for (let key in res) {
              var cCall = new CastingCall(res[key]);
              tmp.push(cCall);
            }

            this.castingCalls = of(tmp).pipe();
          }
        },
        error => {
          this.castingCalls = null;
          // this.chRef.detectChanges();
        }
      );
  }
}
