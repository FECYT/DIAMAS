import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleDropdownComponent } from './module-dropdown.component';

describe('ModuleDropdownComponent', () => {
  let component: ModuleDropdownComponent;
  let fixture: ComponentFixture<ModuleDropdownComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModuleDropdownComponent]
    });
    fixture = TestBed.createComponent(ModuleDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
