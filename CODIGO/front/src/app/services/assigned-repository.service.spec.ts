import { TestBed } from '@angular/core/testing';

import { AssignedRepositoryService } from './assigned-repository.service';

describe('AssignedRepositoryService', () => {
  let service: AssignedRepositoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssignedRepositoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
