import { Component, ChangeDetectorRef, OnInit, Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Country } from './types/country';
import { Router, ActivatedRoute } from '@angular/router';
import { UserHolderService } from './user-holder.service';


@Component({
  selector: 'app-my-profile-update',
  templateUrl: './my-profile-update.component.html'
})


export class MyProfileUpdateComponent implements OnInit {
  public model: any;
  public modelCountry: any;
  public cities: any;
  public countries: Observable<Country[]>;
  public fbUserId: any;
  public fbName: any;
  public fbEmail: any;
  public fbProfilePic: any;
  public phoneNumber: any;
  public dateOfBirth: any;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.cities.filter(v => v['name'].toLowerCase().indexOf(term.toLowerCase()) > -1).map(v => v['name']).slice(0, 10))
    )

  ngOnInit() {
      console.log('Initializing Profile Update Component ');
      this.userHolder.currentMessage.subscribe( message => {
        this.fbEmail = message['fbEmail'];
        this.fbName = message['fbName'];
        this.fbProfilePic = message['fbProfilePic'];
        this.fbUserId = message['fbUserId'];

        console.log( 'Message From My Profile Update Component ' + message );
      } );

      if (this.fbUserId == null) {
        this.router.navigate(['/']);
      }

    this.httpClient.get(environment.apiUrl + 'global/countries' ).subscribe((res) => {
      if (res != null) {
        let tmp: Country[] = [];
        for (let key in res) {
          var country = new Country(res[key]);
          tmp.push( country );
        }
        this.countries = of(tmp).pipe();
      } else {
        this.countries = null;
      }
    console.log('inside' + res);
    },
    (error) => {
    this.countries = null;
    // this.chRef.detectChanges();
  });

    }
    setCountry(value: string ) {
      this.httpClient.get(environment.apiUrl + 'global/cities/' + value ).subscribe((res) => {
        if (res != null) {
          this.cities = res;
        } else {
          this.cities = null;
        }
      },
    (error) => {
      this.cities = null;
      // this.chRef.detectChanges();
    });

    }

    /*validate() {
      var city = ((document.getElementById('city') as HTMLInputElement).value);
      if (city == null) {

      }
    }*/

    constructor(private httpClient: HttpClient, private userHolder: UserHolderService, private route: ActivatedRoute ,
      private chRef: ChangeDetectorRef, private router: Router) {


    }
}
