import {Component } from '@angular/core';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, map} from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-my-profile-update',
  templateUrl: './my-profile-update.component.html'
})
export class MyProfileUpdateComponent {
  public model: any;
  public modelCountry: any;
  public cities: any;
  public countries: any;
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


    constructor(private httpClient: HttpClient) {
      this.httpClient.get(environment.apiUrl + 'global/cities/IN' ).subscribe((res) => {
        if (res != null) {
          this.cities = res;
        }

        else {
          this.cities = null;
        }
      console.log('inside' + res);
      },
    (error) => {
      this.cities = null;
    });

    this.httpClient.get(environment.apiUrl + 'global/countries' ).subscribe((res) => {
      if (res != null) {
        this.countries = res;
      }

      else {
        this.countries = null;
      }
    console.log('inside' + res);
    },
  (error) => {
    this.cities = null;
  });

    }
}
