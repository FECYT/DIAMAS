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
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';
import { CacheService } from 'src/app/services/cache.service';

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

declare module 'src/app/interfaces/question.interface' {
  interface Question {
    description: string;
    finishDate: Date;
  }
}

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
  selector: 'app-exportar-documentos',
  templateUrl: './exportar-documentos.component.html',
  styleUrls: ['./exportar-documentos.component.scss']
})
export class ExportarDocumentosComponent implements OnInit {
  selectYear: Date | null = null;
  selection = new SelectionModel<any>(true, []);
  displayedColumns: string[] = ['select', 'periodo', 'institution', 'userName', 'userSurname', 'fechaFin', 'titulo', 'categoria', 'peso'];
  dataSource = new MatTableDataSource<Question>([]);
  dataNotFound: boolean = false;
  periods: EvaluationPeriod[] = [];
  dateRange: Date[] = [];
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];

  dateFilter = (d: Date | null): boolean => {
    this.selection.clear();
    let date = new Date(d!);
    if (!d) {
      return false; // Deshabilitar fechas nulas
    }
    const year = date.getFullYear();
    return this.dateRange.some(allowedYear => allowedYear.getFullYear() === year);
    // return (d! >= this.dateRange[0] && d! <= this.dateRange[1]);
  }

  @ViewChild('picker', { static: false })
  private picker!: MatDatepicker<Date>;

  onFieldsChange() {
    if (this.selectedType !== null && this.selectedType.id !== undefined && this.selectYear) {
      // Ambos campos están seleccionados, realiza la llamada que necesitas
      // const questionnaireID = (this.moduloSeleccionado === 'revista') ? 1 : 2;

      this.questionService.getQuestionsWithFileByYear(this.selectedType.id, this.selectYear!.getFullYear()).subscribe((res) => {
        this.dataSource.data = res;
        if (this.dataSource.data.length === 0) {
          this.dataNotFound = true;
        } else {
          this.dataNotFound = false;
        }
      });
    }
  }

  chosenYearHandler(ev: any) {
    let { _d } = ev;
    this.selectYear = _d;
    this.picker.close();
    this.onFieldsChange();
  }

  constructor(public cacheService: CacheService, private messageService: MessageService, private translate: TranslateService, private questionService: QuestionService, private evaluationPeriodService: EvaluationPeriodService, private fileService: FileService) {
  }

  ngOnInit(): void {
    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }

    this.evaluationPeriodService.findAll().subscribe((periods: EvaluationPeriod[]) => {
      const yearsUsed = this.extractYears(periods);
      this.periods = periods;

      this.dateRange = yearsUsed.map(year => new Date(year, 0, 1));
    });
  }

  onSubmit() {
    let selectedQuestions = this.selection.selected;
    let idList = selectedQuestions.map(question => question.questionId);
    this.fileService.downloadZipByIds(idList).subscribe(
      (data: any) => {
        // Verificar si la respuesta es un Blob
        if (data instanceof Blob) {
          const blob = new Blob([data], { type: 'application/zip' });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          if (this.cacheService.getLanguage() === 'en') {
            a.download = "certificates.zip"
          } else {
            a.download = "certificados.zip"
          }
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
          document.body.removeChild(a);
        } else {
          // Manejar el caso en que la respuesta no sea un Blob
          console.error('La respuesta no es un archivo Blob.');
          // Puedes mostrar un mensaje de error al usuario aquí
        }
      },
      (error) => {
        // Manejar errores aquí
      }
    );
  }

  isAllSelected(): boolean {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  toggleAll(): void {
    this.isAllSelected() ? this.selection.clear() : this.dataSource.data.forEach((row: any) => this.selection.select(row));
  }

  private extractYears(evaluations: EvaluationPeriod[]): number[] {
    // Función para solo mostrar en el selector los años disponibles
    const yearsUsed: number[] = [];

    evaluations.forEach(evaluation => {
      const startYear = new Date(evaluation.startDate).getFullYear();
      const finishYear = new Date(evaluation.finishDate).getFullYear();

      for (let year = startYear; year <= finishYear; year++) {
        if (!yearsUsed.includes(year)) {
          yearsUsed.push(year);
        }
      }
    });
    return yearsUsed;
  }

  private extractIdsAndDescriptionsByYear(evaluations: EvaluationPeriod[], selectedYear: number): { id: number, description: string, finishDate: Date }[] {
    const matchingIdsWithDescriptions: { id: number, description: string, finishDate: Date }[] = [];

    evaluations.forEach(evaluation => {
      const startYear = new Date(evaluation.startDate).getFullYear();
      const finishYear = new Date(evaluation.finishDate).getFullYear();

      if (startYear <= selectedYear && finishYear >= selectedYear) {
        // Si el año seleccionado está dentro del rango de la evaluación
        matchingIdsWithDescriptions.push({
          id: evaluation.id,
          description: evaluation.description,
          finishDate: evaluation.finishDate
        });
      }
    });

    return matchingIdsWithDescriptions;
  }

  private extractIdsByYear(evaluations: EvaluationPeriod[], selectedYear: number): number[] {
    const matchingIds: number[] = [];

    evaluations.forEach(evaluation => {
      const startYear = new Date(evaluation.startDate).getFullYear();
      const finishYear = new Date(evaluation.finishDate).getFullYear();

      if (startYear <= selectedYear && finishYear >= selectedYear) {
        // Si el año seleccionado está dentro del rango de la evaluación
        matchingIds.push(evaluation.id);
      }
    });

    return matchingIds;
  }

}
