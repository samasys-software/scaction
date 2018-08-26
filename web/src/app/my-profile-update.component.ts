import { Component, ChangeDetectorRef, OnInit, Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { SelectModule } from 'ng2-select';
import { Country } from './types/country';
import { Router, Routes, ActivatedRoute } from '@angular/router';
import { UserHolderService } from './user-holder.service';



@Component({
  selector: 'app-my-profile-update',
  templateUrl: './my-profile-update.component.html',
  providers: [UserHolderService]
})

@Injectable()
export class MyProfileUpdateComponent implements OnInit {
  public model: any;
  public modelCountry: any;
  public cities: any;
  public countries: Country[];
  public fbUserId: any;
  public fbName: any;
  public fbEmail: any;
  public fbProfilePic: any;

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.cities.filter(v => v['name'].toLowerCase().indexOf(term.toLowerCase()) > -1).map(v => v['name']).slice(0, 10))
    )

    searchCountry = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.countries.filter(v => v['name'].toLowerCase().indexOf(term.toLowerCase()) > -1).map(v => v['name']).slice(0, 10))
    )

    ngOnInit() {
      this.fbUserId = this.userHolder.fbUserId;

      if (this.fbUserId == null) {
        this.router.navigate(['/']);
      }
      this.fbName = this.userHolder.fbName;
      this.fbEmail = this.userHolder.fbEmail;
      this.fbProfilePic = this.userHolder.fbProfilePic;

    this.httpClient.get(environment.apiUrl + 'global/countries' ).subscribe((res) => {
      if (res != null) {
        this.countries = Object.values(res).map(v => new Country(v));
        /*this.chRef.detectChanges();*/
      }

      else {
        this.countries = null;
         // this.chRef.detectChanges();
      }
    console.log('inside' + res);
    },
  (error) => {
    this.cities = null;
    // this.chRef.detectChanges();
  });


    }

    constructor(private httpClient: HttpClient, private userHolder: UserHolderService, private route: ActivatedRoute ,
      private chRef: ChangeDetectorRef, private router: Router) {


    }
}
