export class CastingCall {

  id: number;
  projectName: string;
  projectDetails: string;
  productionCompany: string;
  roleDetails: string;
  address: string;
  startAge: number;
  endAge: number;
  gender: number;
  countryId: number;
  auditionVenue: number;
  startDate: string;
  endDate: string;
  time: string;
  roleIds: string[];

  constructor(res: any) {

   this.id = res['id'];
   this.projectName = res['projectName'];
   this.projectDetails = res['projectDetails'];
   this.productionCompany = res['productionCompany'];
   this.roleDetails = res['roleDetails'];
   this.address = res['address'];
   this.startAge = res['startAge'];
   this.endAge = res['endAge'];
   this.gender = res['gender'];
   this.countryId = res['countryId'];
   this.auditionVenue = res['cityId'];
   this.startDate = res['startDate'];
   this.endDate = res['endDate'];
   this.time = res['hours'];
   this.roleIds = res['roleIds'];

  }
 }
