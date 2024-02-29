import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NuevoPeriodoComponent } from './nuevo-periodo.component';

describe('NuevoPeriodoComponent', () => {
  let component: NuevoPeriodoComponent;
  let fixture: ComponentFixture<NuevoPeriodoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NuevoPeriodoComponent]
    });
    fixture = TestBed.createComponent(NuevoPeriodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
