import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadisticasSeleccionComponent } from './estadisticas-seleccion.component';

describe('EstadisticasSeleccionComponent', () => {
  let component: EstadisticasSeleccionComponent;
  let fixture: ComponentFixture<EstadisticasSeleccionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstadisticasSeleccionComponent]
    });
    fixture = TestBed.createComponent(EstadisticasSeleccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
