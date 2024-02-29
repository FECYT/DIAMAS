import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaTerminosComponent } from './lista-terminos.component';

describe('ListaTerminosComponent', () => {
  let component: ListaTerminosComponent;
  let fixture: ComponentFixture<ListaTerminosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListaTerminosComponent]
    });
    fixture = TestBed.createComponent(ListaTerminosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
