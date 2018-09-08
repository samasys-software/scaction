import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CastingCallComponent } from './casting-call.component';

describe('CastingCallComponent', () => {
  let component: CastingCallComponent;
  let fixture: ComponentFixture<CastingCallComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CastingCallComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CastingCallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
