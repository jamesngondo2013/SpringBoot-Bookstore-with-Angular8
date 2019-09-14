import { TestBed } from '@angular/core/testing';

import { GetBookListServiceService } from './get-book-list-service.service';

describe('GetBookListServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetBookListServiceService = TestBed.get(GetBookListServiceService);
    expect(service).toBeTruthy();
  });
});
