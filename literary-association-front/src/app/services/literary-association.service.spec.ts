import { TestBed } from '@angular/core/testing';

import { LiteraryAssociationService } from './literary-association.service';

describe('LiteraryAssociationService', () => {
  let service: LiteraryAssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LiteraryAssociationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
