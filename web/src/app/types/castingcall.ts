export class CastingCall {

  id: number;
  projectName: string;
  projectDetails: string;
  productionCompany: string;
  roleDetails: string;
  address: string;

  constructor(res: any) {

   this.id = res['id'];
   this.projectName = res['projectName'];
   this.projectDetails = res['projectDetails'];

   this.productionCompany = res['productionCompany'];
   this.roleDetails = res['roleDetails'];
   this.address = res['address'];
  }
 }
