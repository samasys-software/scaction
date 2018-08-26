import { TestBed, inject } from '@angular/core/testing';

import { UserHolderService } from './user-holder.service';

describe('UserHolderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserHolderService]
    });
  });

  it('should be created', inject([UserHolderService], (service: UserHolderService) => {
    expect(service).toBeTruthy();
  }));
});
