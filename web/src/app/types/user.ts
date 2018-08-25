export class User {
  userId: number;
  role: number;
  fbUser: string;
  fbEmail: string;
  profilePic: string;
  phoneNumber: string;
  countryCode: string;
  whatsappNumber: string;
  fbName: string;
  verified: boolean;
  constructor(res: any) {
    this.userId = res['userId'];
    this.role = res['role'];
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
