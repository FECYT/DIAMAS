import { TestBed } from '@angular/core/testing';

import { CatQuestionService } from './cat-question.service';

describe('CatQuestionService', () => {
  let service: CatQuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CatQuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
