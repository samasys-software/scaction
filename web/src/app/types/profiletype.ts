export class ProfileType {
    id:  string;
    name: string;
   
    constructor(res: any) {
     this.id = res['id'];
     this.name = res['name'];
    }
   }
   