import { TestBed } from '@angular/core/testing';

import { EvaluationActionHistoryService } from './evaluation-action-history.service';

describe('EvaluationActionHistoryService', () => {
  let service: EvaluationActionHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluationActionHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
