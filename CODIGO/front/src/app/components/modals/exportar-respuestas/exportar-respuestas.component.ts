import { Component, ViewChild } from '@angular/core';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter } from "@angular/material-moment-adapter";
import { QuestionnaireService } from 'src/app/services/questionnaire.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UtilService } from 'src/app/services/general/util.service';
import {CacheService} from "../../../services/cache.service";
import {exportTranslations} from "../../../global-constants";
import { TranslateService } from '@ngx-translate/core';
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
  selector: 'app-exportar-respuestas',
  templateUrl: './exportar-respuestas.component.html',
  styleUrls: ['./exportar-respuestas.component.scss']
})
export class ExportarRespuestasComponent  {
  selectYear: Date | null = null;
  selectRange: Date | null = null;
  formatoSeleccionado: string = '';
  exportar: FormGroup;
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];

  @ViewChild('picker', { static: false })
  private picker!: MatDatepicker<Date>;

  chosenYearHandler(ev: any) {
    let { _d } = ev;
    this.selectYear = _d;
    this.picker.close();
    setTimeout(() => {
      this.checkDate();
    }, 0);
  }

  @ViewChild('pickerRange', { static: false })
  private pickerRange!: MatDatepicker<Date>;

  chosenRangeHandler(ev: any) {
    let { _d } = ev;
    this.selectRange = _d;
    this.pickerRange.close();
    setTimeout(() => {
      this.checkDate();
    }, 0);
  }

  constructor(private translate: TranslateService, public cacheService:CacheService,private questionnaireService: QuestionnaireService, private formBuilder: FormBuilder, private utilService: UtilService) {
    this.formatoSeleccionado = 'csv';

    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }

    this.exportar = this.formBuilder.group({
      questionnaireType: this.selectedType,
      startDate: [null, Validators.required],
      finishDate: [null, Validators.required],
      formato: [null, Validators.required]
    });
  }

  checkDate() {
    if(this.selectYear !==null && this.selectRange !== null) {
      if (this.selectYear.getFullYear() > this.selectRange.getFullYear()) {
        this.exportar.get('finishDate')?.setErrors({ dateError: true });
      } else {
        this.exportar.get('finishDate')?.setErrors(null);
      }
    }
  }

  getExportTranslation(key:string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  onSubmit() {
    if (this.selectedType === null) {
      alert(this.translate.instant('SELECCIONA_MODULO'))
      return ;
    }
    
    // const questionnaireID = (this.selectedType === 'revista') ? 1 : 2;

    if (this.selectYear !== null && this.selectRange !== null && this.selectedType.id != undefined) {
      let language = this.cacheService.getLanguage();

      if (this.selectYear.getFullYear() == this.selectRange.getFullYear()) {
        // Hacer una copia de this.selectRange para evitar modificar la fecha original
        const lastDayOfYear = new Date(this.selectRange);

        // Establecer la fecha al último día del año
        lastDayOfYear.setFullYear(lastDayOfYear.getFullYear() + 1);
        lastDayOfYear.setDate(lastDayOfYear.getDate() - 1);

        // Asignar la fecha al atributo selectRange
        this.selectRange = lastDayOfYear;
      }

      this.questionnaireService.downloadAnswer(this.selectedType.id, this.selectYear, this.selectRange, language, this.formatoSeleccionado)
        .subscribe((data: any) => {
          // Verificar si la respuesta es un Blob
          if (data instanceof Blob) {
            // Crear un Blob con la respuesta del servicio
            const blob = new Blob([data], { type: 'application/octet-stream' });

            // Crear una URL para el Blob
            const url = window.URL.createObjectURL(blob);

            // Crear un elemento de ancla invisible
            const a = document.createElement('a');

            // Establecer el nombre del archivo
            const fileName = this.translate.instant('RESPONSES') + (this.formatoSeleccionado === 'excel' ? '.xlsx' : '.csv');
            a.href = url;
            a.download = fileName; // Establecer el nombre del archivo dinámicamente según el formato seleccionado
            document.body.appendChild(a);

            // Hacer clic en el enlace para iniciar la descarga
            a.click();

            // Liberar el objeto URL y eliminar el elemento de ancla
            window.URL.revokeObjectURL(url);
            document.body.removeChild(a);
          } else {
            // Manejar el caso en que la respuesta no sea un Blob
            console.error('La respuesta no es un archivo Blob.');
            // Puedes mostrar un mensaje de error al usuario aquí
          }
        }, error => {
          this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_EXPORT"));
        });
    } else {
      // Posible mensaje de error
    }

  }
}

