import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarVocabularioComponent } from './agregar-vocabulario.component';

describe('AgregarVocabularioComponent', () => {
  let component: AgregarVocabularioComponent;
  let fixture: ComponentFixture<AgregarVocabularioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgregarVocabularioComponent]
    });
    fixture = TestBed.createComponent(AgregarVocabularioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
