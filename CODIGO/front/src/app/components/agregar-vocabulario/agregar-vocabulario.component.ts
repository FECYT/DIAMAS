import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-agregar-vocabulario',
  templateUrl: './agregar-vocabulario.component.html',
  styleUrls: ['./agregar-vocabulario.component.scss']
})
export class AgregarVocabularioComponent {

  vocabularioForm: FormGroup;

  constructor(private router: Router, private dialog: MatDialog) {    
    this.vocabularioForm = new FormGroup({
      nombre: new FormControl('', Validators.required),
      descripcion: new FormControl('')

    });
    
  }
  guardarVocabulario() {
    if (this.vocabularioForm.valid) {
        // Aquí simulo una llamada de API con una promesa. Puedes reemplazar esto con una llamada real.
      this.fakeApiCall().then(
        () => {
          // Mostrar modal de éxito
          this.dialog.open(SuccessModalComponent, {
            data: {
              title: 'Éxito',
              content: 'Formulario guardado con éxito.'
            },
            width: '70%',
            height: '70%'
          });
        },
        () => {
          // Mostrar modal de error
          this.dialog.open(SuccessModalComponent, {
            data: {
              title: 'Error',
              content: 'Hubo un error al guardar el formulario.'
            },
            width: '70%',
            height: '70%'
          });
        }
      );
      console.log(this.vocabularioForm.value);
    }
  }

  ngOnInit() {
  }

  
  // Simulación de una llamada de API
  fakeApiCall(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (Math.random() > 0.5) {
          resolve(true);
        } else {
          reject(false);
        }
      }, 2000);
    });
  }

}
