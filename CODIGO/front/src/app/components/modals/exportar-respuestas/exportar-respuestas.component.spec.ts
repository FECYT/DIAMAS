import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarRespuestasComponent } from './exportar-respuestas.component';

describe('ExportarRespuestasComponent', () => {
  let component: ExportarRespuestasComponent;
  let fixture: ComponentFixture<ExportarRespuestasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarRespuestasComponent]
    });
    fixture = TestBed.createComponent(ExportarRespuestasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
