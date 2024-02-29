import { TestBed } from '@angular/core/testing';

import { AssignedEvaluatorService } from './assigned-evaluator.service';

describe('AssignedEvaluatorService', () => {
  let service: AssignedEvaluatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssignedEvaluatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
