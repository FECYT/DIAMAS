import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaEvaluacionesComponent } from './lista-evaluaciones.component';

describe('ListaEvaluacionesComponent', () => {
  let component: ListaEvaluacionesComponent;
  let fixture: ComponentFixture<ListaEvaluacionesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaEvaluacionesComponent]
    });
    fixture = TestBed.createComponent(ListaEvaluacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
