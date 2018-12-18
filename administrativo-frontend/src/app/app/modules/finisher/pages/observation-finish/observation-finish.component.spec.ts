import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObservationFinishComponent } from './observation-finish.component';

describe('ObservationFinishComponent', () => {
  let component: ObservationFinishComponent;
  let fixture: ComponentFixture<ObservationFinishComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObservationFinishComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObservationFinishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
