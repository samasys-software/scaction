import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from '../../environments/environment';
import { Observable, of } from 'rxjs';
import { Country } from '../types/country';
import { City } from '../types/city';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { ProfileType } from '../types/profiletype';
import { User } from '../types/user';
import { CastingCall } from '../types/castingcall';
import { CastingCallService } from '../casting-call.service';

@Component({
  selector: 'app-casting-call',
  templateUrl: './casting-call.component.html',
  styleUrls: ['./casting-call.component.css']
})
export class CastingCallComponent implements OnInit {

  public projectName: any;
  public projectDetails: any;
  public productionCompany: any;
  public roleDetail: any;
  public startAge: any;
  public endAge: any;
  public startDate: any;
  public endDate: any;
  public gender: any;
  public countries: Observable<Country[]>;
  public profileTypes: Observable<ProfileType[]> = new Observable<ProfileType[]>();
  public modelCountry: any;
  public cities: City[];
  public auditionVenue: any;
  public address: any;
  public time: any;
  public user: User;
  public castingCallId = '-1';

  @ViewChild('content') private content;

  constructor(private httpClient: HttpClient, private modalService: NgbModal) { }

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

    this.httpClient.get(environment.apiUrl + 'global/profileDefaults').subscribe((res) => {
      if (res != null) {
        const countryResp = res['countries'];
        const profileResp = res['profileTypes'];

        const tmp: Country[] = [];
        for (let key in countryResp) {
          var country = new Country(countryResp[key]);
          tmp.push(country);
        }

        const pTmp: ProfileType[] = [];
        for (let key in profileResp) {
          var profile = new ProfileType(profileResp[key]);
          pTmp.push(profile);
        }

        this.profileTypes = of(pTmp).pipe();
        this.countries = of(tmp).pipe();

        if ( this.user != null) {

          this.user.userRoles.forEach((entry) => {
            this.profileTypes.subscribe((tProfileTypes) => {
              tProfileTypes.forEach((profileType) => {
                if ( entry['roleType']['id'] === profileType.id) {
                  profileType.checked = true;
                }
              });
            });
          });
        }

      } else {
        this.countries = null;
      }
      console.log('inside' + res);
    },
      (error) => {
        this.countries = null;
        // this.chRef.detectChanges();
      });

      const localCastingCallItem = localStorage.getItem('castingCall');
    localStorage.removeItem('castingCall');
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
      this.modelCountry = castingCall.countryId;
      this.auditionVenue = castingCall.auditionVenue;
      this.startDate = castingCall.startDate;
      this.endDate = castingCall.endDate;
      this.time = castingCall.time;

    }


  }
  setCountry() {
    let value = this.modelCountry.code;
    if (this.modelCountry == null) { value = ''; }
    if (value.length > 0) {}
      this.httpClient.get(environment.apiUrl + 'global/cities/' + value).subscribe((res) => {

        if (res != null) {
          const tmp: City[] = [];
          for (let key in res) {
            tmp.push(new City(res[key]));
          }
          this.cities = tmp;
        } else {
          this.cities = null;
        }
      },
        (error) => {
          this.auditionVenue = null;
          // this.chRef.detectChanges();
        });

  }



  castingCallregister(form: any) {
    const formData = new FormData();

    formData.append('castingCallId', this.castingCallId);
    formData.append('projectName', this.projectName);
    formData.append('projectDetails', this.projectDetails);
    formData.append('productionCompany', this.productionCompany);
    formData.append('roleDetails', this.roleDetail);
    formData.append('startAge', this.startAge);
    formData.append('endAge', this.endAge);
    formData.append('gender', this.gender);
    formData.append('countryId', this.modelCountry.id);
    formData.append('cityId', this.auditionVenue.id);
    formData.append('startDate', this.startDate);
    formData.append('endDate', this.endDate);
    formData.append('address', 'chennai 600008');

    this.profileTypes.forEach((entry) => {
      entry.forEach((profileType) => {
        if (profileType.checked) {
          formData.append('roles', profileType.id);
        }
      });
    });

    formData.append('hours', this.time);
    const localUserItem = localStorage.getItem('user');
    this.user = JSON.parse(localUserItem);
    formData.append('userId', '' + this.user.userId);

    this.httpClient.post(environment.apiUrl + 'global/castingCall', formData).subscribe((res) => {
      console.log(res);
      this.modalService.open(this.content, { ariaLabelledBy: 'modal-basic-title' });
    },
      (error) => {
        console.log(error);
      });

  }

}
