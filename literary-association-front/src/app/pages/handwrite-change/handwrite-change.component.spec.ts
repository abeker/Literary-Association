import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HandwriteChangeComponent } from './handwrite-change.component';

describe('HandwriteChangeComponent', () => {
  let component: HandwriteChangeComponent;
  let fixture: ComponentFixture<HandwriteChangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HandwriteChangeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HandwriteChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
