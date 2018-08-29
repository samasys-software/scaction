import { Component, ChangeDetectorRef, OnInit, Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { Country } from './types/country';
import { Router, ActivatedRoute } from '@angular/router';
import { UserHolderService } from './user-holder.service';
import { FormsModule } from '@angular/forms';
import {MatChipsModule} from "@angular/material/chips"
import {ProfileType} from './types/profiletype';
@Component({
  selector: 'app-my-profile-update',
  templateUrl: './my-profile-update.component.html'
})


export class MyProfileUpdateComponent implements OnInit {
  public modelCities: any;
  public modelCountry: any;
  public profileTypes: Observable<ProfileType[]>;
  public cities: any;
  public countries: Observable<Country[]>;
  public screenName: any;
  public fbUserId: any;
  public fbName: any;
  public fbEmail: any;
  public fbProfilePic: any;
  public phoneNumber: any;
  public whatsappNumber: any;
  public isSameASPhone: boolean;
  public dateOfBirth: any;
  public roles: any;

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

    this.httpClient.get(environment.apiUrl + 'global/profileDefaults' ).subscribe((res) => {
      if (res != null) {
        let countryResp = res['countries'];
        let profileResp = res['profileTypes'];

        let tmp: Country[] = [];
        for (let key in countryResp) {
          var country = new Country(countryResp[key]);
          tmp.push( country );
        }
        let pTmp: ProfileType[]=[];
        for( let key in profileResp ){
          var profile = new ProfileType( profileResp[key]);
          pTmp.push( profile );
        }

        this.profileTypes = of(pTmp).pipe();
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

    register(form: any) {

      console.log(form);
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
