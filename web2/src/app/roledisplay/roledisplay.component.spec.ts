import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoledisplayComponent } from './roledisplay.component';

describe('RoledisplayComponent', () => {
  let component: RoledisplayComponent;
  let fixture: ComponentFixture<RoledisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoledisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoledisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
