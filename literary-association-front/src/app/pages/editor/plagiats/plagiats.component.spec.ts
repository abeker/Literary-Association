import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlagiatsComponent } from './plagiats.component';

describe('PlagiatsComponent', () => {
  let component: PlagiatsComponent;
  let fixture: ComponentFixture<PlagiatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlagiatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlagiatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
