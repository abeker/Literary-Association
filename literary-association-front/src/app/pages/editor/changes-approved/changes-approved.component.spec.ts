import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangesApprovedComponent } from './changes-approved.component';

describe('ChangesApprovedComponent', () => {
  let component: ChangesApprovedComponent;
  let fixture: ComponentFixture<ChangesApprovedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangesApprovedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangesApprovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
