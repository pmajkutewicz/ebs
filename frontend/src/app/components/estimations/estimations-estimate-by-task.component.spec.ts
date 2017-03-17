import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EstimationsEstimateByTaskComponent } from './estimations-estimate-by-task.component';

describe('EstimationsEstimateByTaskComponent', () => {
  let component: EstimationsEstimateByTaskComponent;
  let fixture: ComponentFixture<EstimationsEstimateByTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EstimationsEstimateByTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EstimationsEstimateByTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
