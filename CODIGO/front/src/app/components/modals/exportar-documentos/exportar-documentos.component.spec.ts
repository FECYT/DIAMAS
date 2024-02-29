import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarDocumentosComponent } from './exportar-documentos.component';

describe('ExportarDocumentosComponent', () => {
  let component: ExportarDocumentosComponent;
  let fixture: ComponentFixture<ExportarDocumentosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarDocumentosComponent]
    });
    fixture = TestBed.createComponent(ExportarDocumentosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
