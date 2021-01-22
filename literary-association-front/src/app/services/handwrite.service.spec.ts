import { TestBed } from '@angular/core/testing';

import { HandwriteService } from './handwrite.service';

describe('HandwriteService', () => {
  let service: HandwriteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HandwriteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
