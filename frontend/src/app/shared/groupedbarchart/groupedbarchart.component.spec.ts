import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupedbarchartComponent } from './groupedbarchart.component';

describe('GroupedbarchartComponent', () => {
  let component: GroupedbarchartComponent;
  let fixture: ComponentFixture<GroupedbarchartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupedbarchartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupedbarchartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
