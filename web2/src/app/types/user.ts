export class User {
    userId: number;
    fbUser: string;
    screenName: string;
    fbEmail: string;
    countryCode: string;
    cityId: any;
    phoneNumber: string;
    whatsappNumber: string;
    gender: string;
    dateOfBirth: any;
    searchable: any;
    profilePic: string;
    role: number;
    userRoles: any[];
    fbName: string;
    verified: boolean;
    constructor(res: any) {
      this.userId = res['userId'];
      this.role = res['role'];
      this.userRoles = res['userRoles'];
      this.fbUser = res['fbUser'];
      this.fbEmail = res['fbEmail'];
      this.screenName = res['screenName'];
      this.profilePic = res['profilePic'];
      this.phoneNumber = res['phoneNumber'];
      this.countryCode = res['countryCode'];
      this.cityId = res['cityId'];
      this.whatsappNumber = res['whatsappNumber'];
      this.fbName = res['fbName'];
      this.gender = res['gender'];
      this.dateOfBirth = res['dateOfBirth'];
      this.verified = res['verified'];
      this.searchable = res['searchable'];
    }
  }