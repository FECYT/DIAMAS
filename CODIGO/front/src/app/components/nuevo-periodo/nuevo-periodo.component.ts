import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EvaluationPeriod } from '../../interfaces/evaluation-period.interface';
import { EvaluationPeriodService } from 'src/app/services/evaluation-period.service';
import { QuestionService } from "../../services/question.service";
import { Question } from "../../interfaces/question.interface";
import { DomSanitizer } from "@angular/platform-browser";
import { UtilService } from 'src/app/services/general/util.service';
import { QuestionModalComponent } from '../question-modal/question-modal.component';
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";
import { TranslateService } from "@ngx-translate/core";
import { QuestionnaireType } from '../../interfaces/questionnaire-type.interface';

@Component({
  selector: 'app-nuevo-periodo',
  templateUrl: './nuevo-periodo.component.html',
  styleUrls: ['./nuevo-periodo.component.scss']
})
export class NuevoPeriodoComponent {
  displayedColumns: string[] = ['orden', 'titulo', 'categoria', 'evaluable', 'peso', 'textoAyuda', 'acciones'];
  dataSource: Question[] = [];
  showSpinner = false;
  periodo: FormGroup;
  periodoId: number | undefined;
  editPeriodo: EvaluationPeriod = {} as EvaluationPeriod;
  selectedStatus = 'Abierto';
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];

  constructor(private translateService: TranslateService, public cacheService: CacheService, private activatedRoute: ActivatedRoute, private router: Router, private dialog: MatDialog, private formBuilder: FormBuilder, private evaluationPeriodService: EvaluationPeriodService, private questionService: QuestionService, private sanitizer: DomSanitizer, private utilService: UtilService) {
    const id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id !== null) {
      this.periodoId = +id; // Convierte el string a un número
    }

    if (this.periodoId) {
      // Es una edición
      this.evaluationPeriodService.findById(this.periodoId).subscribe(term => {
        this.updateFormWithTermData(term);
        this.getRelativeQuestions(term);
        this.editPeriodo = term;
      });
    }

    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }

    this.periodo = this.formBuilder.group({
      id: null, // O un valor numérico inicial si es necesario
      startDate: [null, Validators.required],
      finishDate: [null],
      status: this.selectedStatus,
      description: '',
      questionnaireType: this.selectedType,
      deleteState: 1
    });
  }  // Inyecta Router en el constructor

  nuevaPregunta() {
    this.router.navigate(['/nueva-pregunta'], { state: { periodoId: this.periodoId } });  // Navega a la nueva ruta
  }

  editarPregunta(row: any) {
    this.router.navigate(['/nueva-pregunta'], { state: { periodoId: this.periodoId, question: row } });  // Navega a editar
  }

  getLanguagedText(pregunta: any) {


    switch (this.cacheService.getLanguage().toLowerCase()) {
      case "es":
        return pregunta.esp
      default:
        return pregunta.eng
    }

  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  eliminarPregunta(row: any) {
    let dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_ELIMINAR_QUESTION"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ELIMINAR_QUESTION")),
      },
      width: '50%',
      height: '60%'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.questionService.delete(row.id).subscribe({
          next: () => {
            this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("EXITO_ELIMINAR_QUESTION"));
            let indexToDelete = this.dataSource.findIndex(question => question.id === row.id);
            // Si se encuentra el elemento con el ID dado, elimínalo del array
            if (indexToDelete !== -1) {
              this.dataSource.splice(indexToDelete, 1);
            }
            // Nueva referencia para que detecte los cambios
            this.dataSource = [...this.dataSource];
          },
          error: error => {
            this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_ELIMINAR_QUESTION"));
          }
        });
      } else {
        // Posible toastr para indicar que se ha cancelado el borrado
        //console.log('Se ha cancelado el borrado');
      }
    });
  }

  guardarPeriodo() {

    /*    if (this.moduloSeleccionado == '') {
          alert(this.translateService.instant('SELECCIONA_MODULO'))
          return ;
        }*/

    this.showSpinner = true
    if (this.periodoId) {
      let periodo: EvaluationPeriod = this.periodo.getRawValue();
      // Necesario para convertir string a date en HTML5
      periodo.startDate = new Date(periodo.startDate);
      periodo.finishDate = new Date(periodo.finishDate);

      this.evaluationPeriodService.update(periodo).subscribe(
        (periodoCreado: EvaluationPeriod) => {
          this.showSpinner = false
          this.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("MENSAJE_PERIODO_GUARDADO"));
          this.getRelativeQuestions(periodoCreado);
          this.router.navigate([`/nuevo-periodo/${periodoCreado.id}`]);
        },
        (error) => {
          this.showSpinner = false
          this.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT_CUESTIONARIO") + this.getExportTranslation("ERROR_GUARDAR_PERIODO"));
        }
      );
    } else {
      let periodo: EvaluationPeriod = this.periodo.value;
      // Necesario para convertir string a date en HTML5
      periodo.startDate = new Date(periodo.startDate);
      if (periodo.finishDate !== null) {
        periodo.finishDate = new Date(periodo.finishDate);
      }

      // Ejemplo de type questionnaire
      // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
      /*
      const questionnaireType: QuestionnaireType = {
        id: idType
      };
      */
      /* Este código es el que podría seleccionar el tipo de cuestionario en la pantalla anterior
       const questionnaireType = this.cacheService.selectedTypeValue;
       if(questionnaireType !== null) {
         periodo.questionnaireType = questionnaireType;
       }
       */

      if (this.selectedType != null) {
        periodo.questionnaireType = this.selectedType;

        this.evaluationPeriodService.create(periodo).subscribe(
          (periodoCreado: EvaluationPeriod) => {
            this.showSpinner = false
            this.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("MENSAJE_PERIODO_GUARDADO"));
            this.getRelativeQuestions(periodoCreado);
            this.router.navigate([`/nuevo-periodo/${periodoCreado.id}`]);
          },
          (error) => {
            this.showSpinner = false
            this.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT_CUESTIONARIO") + this.getExportTranslation("ERROR_GUARDAR_PERIODO"));
          }
        );
      } else {
        alert('Ejemplo module no seleccionado');
      }
    }
  }

  getRelativeQuestions(period: EvaluationPeriod) {
    this.questionService.getQuestionsByPeriodId(period.id).subscribe((questions: Question[]) => {
      this.dataSource = questions
        .filter(question => question.nDeleteState === 0 || question.nDeleteState === 1)
        .sort((a, b) => a.orden - b.orden);
    });
  }

  /**
   * Método privado para manejar mensajes de exito. Muestra un modal.
   * @param success - Mensaje de Éxito.
   */
  handleSuccess(success: any): void {
    this.dialog.open(SuccessModalComponent, {
      data: {
        title: this.translateService.instant('EXITO'),
        content: this.sanitizeHtml(success),
      },
      width: '50%',
      height: '60%'
    });
  }

  /**
   * Método privado para manejar errores. Muestra un modal de error.
   * @param error - Error capturado.
   */
  handleError(error: any): void {
    this.dialog.open(SuccessModalComponent, {
      data: {
        title: 'Error',
        content: this.sanitizeHtml(error),
      },
      width: '50%',
      height: '60%'
    });
  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  private updateFormWithTermData(term: EvaluationPeriod): void {
    this.periodo.patchValue({
      id: term.id,
      startDate: this.formatDate(term.startDate),
      finishDate: this.formatDate(term.finishDate),
      status: term.status,
      description: term.description,
      deleteState: term.deleteState
    });
    this.endDateCheck();
  }

  private formatDate(date: Date) {
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
  }

  public startDateCheck() {
    this.isEndDateBeforeInitialDate();
  }

  public endDateCheck() {
    if (this.isDateBeforeToday(new Date(this.periodo.get('finishDate')!.value))) {
      this.periodo.get('status')?.setValue('Cerrado');
      this.periodo.get('status')?.disable();
    } else {
      this.periodo.get('status')?.enable();
    }
    this.isEndDateBeforeInitialDate();
  }

  public isEndDateBeforeInitialDate() {
    let startDateValue = new Date(this.periodo.get('startDate')!.value);
    let finishDateValue = new Date(this.periodo.get('finishDate')!.value);
    if (this.periodo.get('startDate')!.value && this.periodo.get('finishDate')!.value && startDateValue > finishDateValue) {
      this.periodo.get('finishDate')?.setErrors({ dateError: true });
    } else {
      this.periodo.get('finishDate')?.setErrors(null);
    }
  }

  public isDateBeforeToday(date: Date) {
    return new Date(date.toDateString()) < new Date(new Date().toDateString());
  }
}
