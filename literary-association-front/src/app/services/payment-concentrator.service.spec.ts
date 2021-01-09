import { TestBed } from '@angular/core/testing';

import { PaymentConcentratorService } from './payment-concentrator.service';

describe('PaymentConcentratorService', () => {
  let service: PaymentConcentratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentConcentratorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
