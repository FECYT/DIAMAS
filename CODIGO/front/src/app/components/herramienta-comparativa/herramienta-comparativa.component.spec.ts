import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HerramientaComparativaComponent } from './herramienta-comparativa.component';

describe('HerramientaComparativaComponent', () => {
  let component: HerramientaComparativaComponent;
  let fixture: ComponentFixture<HerramientaComparativaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HerramientaComparativaComponent]
    });
    fixture = TestBed.createComponent(HerramientaComparativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
