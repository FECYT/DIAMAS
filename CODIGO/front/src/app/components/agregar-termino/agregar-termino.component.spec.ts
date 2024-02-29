import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarTerminoComponent } from './agregar-termino.component';

describe('AgregarTerminoComponent', () => {
  let component: AgregarTerminoComponent;
  let fixture: ComponentFixture<AgregarTerminoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgregarTerminoComponent]
    });
    fixture = TestBed.createComponent(AgregarTerminoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
