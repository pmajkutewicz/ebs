import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EstimationsStatsComponent } from './estimations-stats.component';

describe('EstimationsStatsComponent', () => {
  let component: EstimationsStatsComponent;
  let fixture: ComponentFixture<EstimationsStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EstimationsStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EstimationsStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
