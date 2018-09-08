import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CastingCallDetailComponent } from './casting-call-detail.component';

describe('CastingCallDetailComponent', () => {
  let component: CastingCallDetailComponent;
  let fixture: ComponentFixture<CastingCallDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CastingCallDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CastingCallDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
