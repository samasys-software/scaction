export class Country {
 code:  string;
 name: string;

 constructor(res: any) {

  this.code = res['code'];
  this.name = res['name'];
 }
}
