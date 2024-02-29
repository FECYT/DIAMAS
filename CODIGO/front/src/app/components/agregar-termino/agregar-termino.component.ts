import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CatQuestion } from 'src/app/interfaces/cat-question.interface';
import { CatQuestionService } from 'src/app/services/cat-question.service';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { Subject, of } from 'rxjs';
import { catchError, takeUntil } from 'rxjs/operators';
import { QuestionService } from 'src/app/services/question.service';
import { UtilService } from 'src/app/services/general/util.service';
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";
import { QuestionnaireType } from '../../interfaces/questionnaire-type.interface';

@Component({
  selector: 'app-agregar-termino',
  templateUrl: './agregar-termino.component.html',
  styleUrls: ['./agregar-termino.component.scss']
})
export class AgregarTerminoComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  term: CatQuestion | undefined;
  private destroy$ = new Subject<void>();
  termId: number | undefined; // Haz que sea opcional o puede ser indefinido
  useMockData = false; // Añadir esta línea para controlar el uso de datos simulados
  module = ""


  constructor(private cacheService: CacheService, private route: ActivatedRoute, private catQuestionService: CatQuestionService, private router: Router, private dialog: MatDialog, private sanitizer: DomSanitizer, private questionService: QuestionService, private utilService: UtilService) {
    this.formGroup = new FormGroup({
      categoryType: new FormControl('', [Validators.required]),
      tooltipType: new FormControl('', [Validators.required]),
      orden: new FormControl(0, [Validators.min(0), Validators.max(100)])
    });

    // @ts-ignore
    this.module = this.router.getCurrentNavigation()?.extras.state['modulo'];
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id !== null) {
      this.termId = +id; // Convierte el string a un número
    }

    if (this.termId) {
      // Es una edición
      this.catQuestionService.findById(this.termId).subscribe(term => {
        this.updateFormWithTermData(term);
      });

    }
  }

  /**
   *  Método para actualizar el formGroup con los valores del término
   */
  private updateFormWithTermData(term: CatQuestion): void {
    this.formGroup.patchValue({
      categoryType: term.categoryType,
      tooltipType: term.tooltipType,
      orden: term.orden
    });
  }


  /**
   * Método para manejar el envío del formulario.
   */
  submit() {
    // Comprueba si el formulario es válido
    if (this.formGroup.valid) {

      // Ejemplo de type questionnaire
      // const idType = (this.module === 'journal') ? 1 : 2;
      /*
      const questionnaireType: QuestionnaireType = {
        id: idType
      };
      */
      const questionnaireType = this.cacheService.selectedTypeValue;

      // Crea un objeto termToSave a partir de los valores del formulario
      if (questionnaireType !== null) {
        const termToSave: CatQuestion = {
          id: this.termId, // Asigna el ID adecuado si lo tienes, o maneja la lógica de edición
          categoryType: this.formGroup.get('categoryType')!.value,
          tooltipType: this.formGroup.get('tooltipType')!.value,
          orden: this.formGroup.get('orden')!.value,
          hasQuestion: false,
          ndeleteState: 1, // Establece este valor según tu lógica de negocio
          questionnaireType: questionnaireType
        };
        // Si el término tiene un ID, se asume que estás editando un término existente.
        if (termToSave.id) {

          // Usa el método 'update' del servicio para actualizar el término
          this.catQuestionService.update(termToSave.id, termToSave)
            .pipe(takeUntil(this.destroy$)) // Asegura la desuscripción cuando el componente se destruye
            .subscribe({
              next: (response) => {
                console.log('Término actualizado con éxito!', response);
                this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("MENSAJE_TERMINO_ACTUALIZADO"));
                this.router.navigate(['/lista-terminos']);  // Navega a la lista de términos después de una actualización exitosa
              },
              error: (error) => {
                console.log('El término no se pudo actualizar', error);
                this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_ACTUALIZACION_TERMINO"));  // Maneja el error si ocurre alguno durante la actualización
              }
            });

        } else {  // Si el término no tiene un ID, se asume que estás creando un nuevo término.

          // Usa el método 'create' del servicio para guardar el nuevo término
          this.catQuestionService.create(termToSave)
            .pipe(takeUntil(this.destroy$)) // Asegura la desuscripción cuando el componente se destruye
            .subscribe({
              next: (response) => {
                console.log('Término guardado con éxito!', response);
                this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("MENSAJE_TERMINO_GUARDADO"))
                this.router.navigate(['/lista-terminos']);  // Navega a la lista de términos después de una creación exitosa
              },
              error: (error) => {
                console.log('El término no se pudo guardar', error);
                this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_GUARDAR_TERMINO"));  // Maneja el error si ocurre alguno durante la actualización
              }
            });
        }
      }
    }
  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  ngOnDestroy(): void {
    this.destroy$.next();  // Emite un valor, lo que hace que cualquier observador que utilice takeUntil(this.destroy$) se complete
    this.destroy$.complete();  // Marca el subject como completado
  }


}
