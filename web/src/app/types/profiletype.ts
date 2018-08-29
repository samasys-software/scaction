export class ProfileType {
    id:  string;
    name: string;
    checked: boolean;

    constructor(res: any) {
     this.id = res['id'];
     this.name = res['name'];
     this.checked = false;
    }
   }
