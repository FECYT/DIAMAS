import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DescargarCertificadosComponent } from './descargar-certificados.component';

describe('DescargarCertificadosComponent', () => {
  let component: DescargarCertificadosComponent;
  let fixture: ComponentFixture<DescargarCertificadosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DescargarCertificadosComponent]
    });
    fixture = TestBed.createComponent(DescargarCertificadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
