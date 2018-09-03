import { TestBed, inject } from '@angular/core/testing';

import { CastingCallService } from './casting-call.service';

describe('CastingCallService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CastingCallService]
    });
  });

  it('should be created', inject([CastingCallService], (service: CastingCallService) => {
    expect(service).toBeTruthy();
  }));
});
