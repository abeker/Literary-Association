import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BetaReaderCommentComponent } from './beta-reader-comment.component';

describe('BetaReaderCommentComponent', () => {
  let component: BetaReaderCommentComponent;
  let fixture: ComponentFixture<BetaReaderCommentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BetaReaderCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BetaReaderCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
