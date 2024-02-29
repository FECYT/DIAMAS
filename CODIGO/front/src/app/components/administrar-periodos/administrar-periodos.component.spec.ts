import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrarPeriodosComponent } from './administrar-periodos.component';

describe('AdministrarPeriodosComponent', () => {
  let component: AdministrarPeriodosComponent;
  let fixture: ComponentFixture<AdministrarPeriodosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdministrarPeriodosComponent]
    });
    fixture = TestBed.createComponent(AdministrarPeriodosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
