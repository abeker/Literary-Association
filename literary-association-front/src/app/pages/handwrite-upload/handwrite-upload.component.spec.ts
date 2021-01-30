import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HandwriteUploadComponent } from './handwrite-upload.component';

describe('HandwriteUploadComponent', () => {
  let component: HandwriteUploadComponent;
  let fixture: ComponentFixture<HandwriteUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HandwriteUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HandwriteUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
