import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Country } from '../types/country';
import { ProfileType } from '../types/profiletype';
import { City } from '../types/city';
import { User } from '../types/user';
import { HttpClient } from '@angular/common/http';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { CastingCall } from '../types/castingcall';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-casting-call-detail',
  templateUrl: './casting-call-detail.component.html',
  styleUrls: ['./casting-call-detail.component.scss']
})
export class CastingCallDetailComponent implements OnInit {

  public projectName: any;
  public projectDetails: any;
  public productionCompany: any;
  public roleDetail: any;
  public startAge: any;
  public endAge: any;
  public startDate: any;
  public endDate: any;
  public gender: any;
  public countries: Observable<Country[]> = new Observable<Country[]>();
  public profileTypes: Observable<ProfileType[]> = new Observable<ProfileType[]>();
  public modelCountry: any;
  public cities: City[];
  public auditionVenue: any;
  public address: any;
  public time: any;
  public user: User;
  public castingCallId: string;
  private tempCountryId: number;
  private roleIds: string[];
  private readonly = false;
  private castingCallCreatorId: number;

  constructor(private httpClient: HttpClient) { }

  cityFormatter(city: City) {
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

    const localCastingCallItem = localStorage.getItem('castingCall');
    const localUserItem = localStorage.getItem('user');
    this.user = JSON.parse(localUserItem);

    
    if (localCastingCallItem != null) {
      const castingCall: CastingCall = JSON.parse(localCastingCallItem);
      console.log(castingCall);
      this.castingCallId = '' + castingCall.id;
      this.projectName = castingCall.projectName;
      this.projectDetails = castingCall.projectDetails;
      this.productionCompany = castingCall.productionCompany;
      this.roleDetail = castingCall.roleDetails;
      this.address = castingCall.address;
      this.startAge = castingCall.startAge;
      this.endAge = castingCall.endAge;
      this.gender = '' + castingCall.gender;
      this.tempCountryId = castingCall.countryId;
      
      this.auditionVenue = castingCall.auditionVenue;
      this.startDate = castingCall.startDate;
      this.endDate = castingCall.endDate;
      this.time = castingCall.time;
      this.roleIds = castingCall.roleIds;
      this.castingCallCreatorId = castingCall.userId;  

      if( this.user.userId == this.castingCallCreatorId ){
        this.readonly = false;
      }
      else{
        this.readonly = true;
      }
    }
    this.httpClient.get(environment.apiUrl + 'global/profileDefaults').subscribe((res) => {
      if (res != null) {
        const countryResp = res['countries'];
        const profileResp = res['profileTypes'];

        const tmp: Country[] = [];
        for (let key in countryResp) {
          var country = new Country(countryResp[key]);
          if( this.castingCallId != null ){
              if( country.id == this.tempCountryId ){
                 this.modelCountry = country; 
              }
          }
          tmp.push(country);
        }

        const pTmp: ProfileType[] = [];
        for (let key in profileResp) {
          var profile = new ProfileType(profileResp[key]);
          pTmp.push(profile);
        }

        if( this.roleIds != null ){
          this.roleIds.forEach((entry)=>{
            pTmp.forEach((profileType)=>{
               if( profileType.id === entry ) {
                 profileType.checked = true;
               }
            });
          });
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
}
