export class Country {
 code:  string;
 name: string;
 url: string;
 id: number;

 constructor(res: any) {

  this.code = res['code'];
  this.name = res['name'];
  this.url = "/assets/images/countries/"+this.code+".png";
  this.id = res['id'];
 }
}
