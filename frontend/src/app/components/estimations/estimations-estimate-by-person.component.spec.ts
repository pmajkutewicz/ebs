import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EstimationsEstimateByPersonComponent } from './estimations-estimate-by-person.component';

describe('EstimationsEstimateByPersonComponent', () => {
  let component: EstimationsEstimateByPersonComponent;
  let fixture: ComponentFixture<EstimationsEstimateByPersonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EstimationsEstimateByPersonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EstimationsEstimateByPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
