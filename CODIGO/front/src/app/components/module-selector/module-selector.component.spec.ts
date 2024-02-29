import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleSelectorComponent } from './module-selector.component';

describe('ModuleSelectorComponent', () => {
  let component: ModuleSelectorComponent;
  let fixture: ComponentFixture<ModuleSelectorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModuleSelectorComponent]
    });
    fixture = TestBed.createComponent(ModuleSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
