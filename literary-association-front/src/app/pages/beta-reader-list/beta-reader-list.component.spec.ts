import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BetaReaderListComponent } from './beta-reader-list.component';

describe('BetaReaderListComponent', () => {
  let component: BetaReaderListComponent;
  let fixture: ComponentFixture<BetaReaderListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BetaReaderListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BetaReaderListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
