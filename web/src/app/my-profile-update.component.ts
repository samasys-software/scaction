import { Component, ChangeDetectorRef, OnInit, Injectable, ViewChild } from '@angular/core';
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
import { City } from './types/city';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from './types/user';
import { ProfileUpdateService } from './profile-update.service';
@Component({
  selector: 'app-my-profile-update',
  templateUrl: './my-profile-update.component.html'
})


export class MyProfileUpdateComponent implements OnInit {
  public modelCities: any;
  public modelCountry: any;
  public profileTypes: Observable<ProfileType[]>;
  public cities: City[];
  public countries: Observable<Country[]>;
  public screenName: any;
  public fbUserId: any;
  public fbName: any;
  public fbEmail: any;
  public fbProfilePic: any;
  public gender: any;
  public searchable: any;
  public phoneNumber: any;
  public whatsappNumber: any;
  public isSameASPhone: boolean;
  public dateOfBirth: any;
  public roles: any;
  @ViewChild('content') private content;

  cityFormatter( city: City ){
    return city.name;
  }
  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.cities.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
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

      let localUserItem = localStorage.getItem('user');

      if ( localUserItem != null){
        let user: User = JSON.parse(localUserItem);
        this.fbUserId = user.fbUser;
        this.fbEmail = user.fbEmail;
        this.screenName = user.fbName;
      }

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
          let tmp: City[] = [];
          for (let key in res) {
             tmp.push( new City(res[key]));
          }
          this.cities = tmp;
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
       let formData = new FormData();
       formData.append('fbUser' , this.fbUserId);
       formData.append('screenName' , this.screenName);
       formData.append('fbName' , this.fbName);
       formData.append('fbEmail' , this.fbEmail);
       formData.append('countryCode' , this.modelCountry);
       formData.append('cityId' , this.modelCities.id);
       formData.append('phoneNumber' , this.phoneNumber);
       formData.append('whatsappNumber' , this.whatsappNumber);
       formData.append('gender' , this.gender);
       formData.append('dateOfBirth' , this.dateOfBirth);
       formData.append('searchable' , this.searchable);
       formData.append('profilePic' , this.fbProfilePic);

       this.profileTypes.forEach( (entry) => {
         entry.forEach( (profileType) => {
            if ( profileType.checked ) {
              formData.append('roles' , profileType.id );
            }
         });
       });

      this.httpClient.post(environment.apiUrl + '/user/register', formData).subscribe((res) => {
        let user = new User(res);
        localStorage.setItem('user', JSON.stringify(user));
        console.log(res);
        this.modalService.open(this.content, {ariaLabelledBy: 'modal-basic-title'});
        this.profileUpdateService.changeMessage(user);
        this.router.navigate(['/my-home']);
      },
      (error) => {
        console.log(error);
        // this.chRef.detectChanges();
      });
    }


    /*validate() {
      var city = ((document.getElementById('city') as HTMLInputElement).value);
      if (city == null) {

      }
    }*/

    constructor(private httpClient: HttpClient, private userHolder: UserHolderService, private route: ActivatedRoute ,
      private chRef: ChangeDetectorRef, private router: Router, private modalService: NgbModal,
      private profileUpdateService: ProfileUpdateService ) {


    }
}
