import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialAccionesComponent } from './historial-acciones.component';

describe('HistorialAccionesComponent', () => {
  let component: HistorialAccionesComponent;
  let fixture: ComponentFixture<HistorialAccionesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistorialAccionesComponent]
    });
    fixture = TestBed.createComponent(HistorialAccionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
