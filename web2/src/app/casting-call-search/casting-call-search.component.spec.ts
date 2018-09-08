import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CastingCallSearchComponent } from './casting-call-search.component';

describe('CastingCallSearchComponent', () => {
  let component: CastingCallSearchComponent;
  let fixture: ComponentFixture<CastingCallSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CastingCallSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CastingCallSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
