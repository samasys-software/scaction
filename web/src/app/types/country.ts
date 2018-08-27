export class Country {
 code:  string;
 name: string;
 url: string;

 constructor(res: any) {

  this.code = res['code'];
  this.name = res['name'];
  this.url = "/assets/images/countries/"+this.code+".png";
 }
}
