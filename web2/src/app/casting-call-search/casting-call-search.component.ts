import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CastingCall } from '../types/castingcall';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';


@Component({
  selector: 'app-casting-call-search',
  templateUrl: './casting-call-search.component.html',
  styleUrls: ['./casting-call-search.component.scss']
})
export class CastingCallSearchComponent implements OnInit {


  constructor(private httpClient: HttpClient, private router: Router) { }

  public castingCalls: Observable<CastingCall[]>;
  ngOnInit() {
    
    this.httpClient.get(environment.apiUrl + 'global/castingcalls' ).subscribe((res) => {
      if (res != null) {

        const tmp: CastingCall[] = [];
        for (let key in res) {
          var cCall = new CastingCall(res[key]);
          tmp.push( cCall );
        }

        this.castingCalls = of(tmp).pipe();
      }
    },
    (error) => {
      this.castingCalls = null;
    });
  }

  showDetails(castingCall){
    localStorage.setItem('castingCall' , JSON.stringify( castingCall) );
    this.router.navigate(['/casting-call-details']);
  }

}
