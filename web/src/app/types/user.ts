export class User {
  userId: number;
  fbUser: string;
  screenName: string;
  fbEmail: string;
  countryCode: string;
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
    this.profilePic = res['profilePic'];
    this.phoneNumber = res['phoneNumber'];
    this.countryCode = res['countryCode'];
    this.whatsappNumber = res['whatsappNumber'];
    this.fbName = res['fbName'];
    this.verified = res['verified'];
  }
}
