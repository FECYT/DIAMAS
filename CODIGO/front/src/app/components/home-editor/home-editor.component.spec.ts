import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeEditorComponent } from './home-editor.component';

describe('HomeResponsableRepositorioComponent', () => {
  let component: HomeEditorComponent;
  let fixture: ComponentFixture<HomeEditorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeEditorComponent]
    });
    fixture = TestBed.createComponent(HomeEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
