import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CastingCoordinatorComponent } from './casting-coordinator.component';

describe('CastingCoordinatorComponent', () => {
  let component: CastingCoordinatorComponent;
  let fixture: ComponentFixture<CastingCoordinatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CastingCoordinatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CastingCoordinatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
