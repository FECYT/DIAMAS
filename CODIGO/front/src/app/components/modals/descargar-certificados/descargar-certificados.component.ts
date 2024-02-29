import { Component, OnInit, ViewChild } from '@angular/core';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter } from "@angular/material-moment-adapter";
import { QuestionService } from 'src/app/services/question.service';
import { SelectionModel } from '@angular/cdk/collections';
import { EvaluationPeriodService } from 'src/app/services/evaluation-period.service';
import { EvaluationPeriod } from 'src/app/interfaces/evaluation-period.interface';
import { MatTableDataSource } from '@angular/material/table';
import { Question } from 'src/app/interfaces/question.interface';
import { FileService } from 'src/app/services/file.service';
import { QuestionnaireService } from 'src/app/services/questionnaire.service';
import { UtilService } from 'src/app/services/general/util.service';
import { CacheService } from "../../../services/cache.service";
import { exportTranslations } from "../../../global-constants";
import { jsPDF } from "jspdf";
import * as JSZip from 'jszip';
import { TranslateService } from '@ngx-translate/core';
import { UserRepositoryService } from 'src/app/services/user-repository.service';
import { forkJoin } from 'rxjs';
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';

export const MY_FORMATS = {
  parse: {
    dateInput: 'YYYY',
  },
  display: {
    dateInput: 'YYYY',
    monthYearLabel: 'YYYY',
    monthYearA11yLabel: 'YYYY',
  },
};

@Component({
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {
      provide: MAT_DATE_FORMATS, useValue: MY_FORMATS
    },
  ],
  selector: 'app-descargar-certificados',
  templateUrl: './descargar-certificados.component.html',
  styleUrls: ['./descargar-certificados.component.scss']
})
export class DescargarCertificadosComponent {
  selectYear: Date | null = null;
  selection = new SelectionModel<any>(true, []);
  dateRange: Date[] = []
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];

  @ViewChild('picker', { static: false })
  private picker!: MatDatepicker<Date>;

  chosenYearHandler(ev: any) {
    let { _d } = ev;
    this.selectYear = _d;
    this.picker.close();
    /*
    this.questionService.getQuestionsWithFileByYear(this.selectYear!.getFullYear()).subscribe((res) => {
      this.dataSource.data = res;
      if(this.dataSource.data.length === 0) {
        this.dataNotFound = true;
      } else {
        this.dataNotFound = false;
      }
    });
    */
  }

  constructor(private userRepositoryService: UserRepositoryService, private translate: TranslateService, public cacheService: CacheService, private questionnaireService: QuestionnaireService, private utilService: UtilService) {
    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }
  }

  onSubmit() {
    if (this.selectedType === null) {
      alert(this.translate.instant('SELECCIONA_MODULO'))
      return;
    }

    if (this.selectYear !== null && this.selectedType.id !== undefined) {
      this.questionnaireService.downloadCertificatesByYear(this.selectedType.id, this.selectYear)
        .subscribe(async (data: any) => {
          // Bucle para procesar cada certificado y agregarlo al FormData
          const formData = new FormData();
          const fileNames: string[] = [];

          const zipFilePromises = data.map(async (cuestionario: any) => {
            // Llamar a la función downloadReports para generar el PDF y obtener el array de bytes
            const pdfBytes = await this.questionnaireService.downloadReports(cuestionario);
            // Agregar el array de bytes al FormData con un nombre único
            formData.append(
              `pdfFiles`,
              pdfBytes,
              `${cuestionario.evaluation.userRepository.repository.institucion.acronimo}_${cuestionario.evaluation.userRepository.user.nombre}_${cuestionario.evaluation.userRepository.user.apellidos}_${this.translate.instant('CERTIFICATE')}`
            );
          });
          /*
          const zipFilePromises = data.map(async (cuestionario: any, index: any) => {
            // Llamar a la función downloadReports para generar el PDF y obtener el array de bytes
            const pdfBytes = await this.questionnaireService.downloadReports(cuestionario);
            // Agregar el array de bytes al FormData con un nombre único
            formData.append(`pdfFiles`, pdfBytes, `${cuestionario.evaluation.userRepository.repository.institucion.acronimo}_${cuestionario.evaluation.userRepository.user.nombre}_${cuestionario.evaluation.userRepository.user.apellidos}_${this.translate.instant('CERTIFICATE')}`);
          });
          */

          // Esperar a que todas las promesas se resuelvan
          if (zipFilePromises.length > 0) {
            Promise.all(zipFilePromises).then(async () => {
              // Realizar la solicitud POST para enviar los Blobs al backend
              this.questionnaireService.generateAndSendPDFs(formData)
                .then(() => {
                  console.log('Todos los PDFs fueron enviados exitosamente al backend');
                })
                .catch((error) => {
                  console.error('Error al enviar los PDFs al backend:', error);
                });
            });
          } else {
            this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CERTIFICATES"));
          }
        }, error => {
          this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CERTIFICATES"));
        });
    } else {
      // Posible mensaje de error
    }
  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  onFieldsChange() {
    this.cacheService.selectedTypeValue = this.selectedType;
  }

}
