import { Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { HistorialAccionesComponent } from '../historial-acciones/historial-acciones.component';
import { EvaluationService } from 'src/app/services/evaluation.service'; // Asegúrate de importar el servicio
import { Evaluation } from 'src/app/interfaces/evaluation.interface';
import { AuthService } from 'src/app/services/authservice.service';
import { User } from 'src/app/interfaces/user.interface';
import { Observable, Subscription, forkJoin, switchMap } from 'rxjs';
import { EvaluationActionHistory } from 'src/app/interfaces/evaluation-action-history.interface';
import { EvaluationActionHistoryService } from 'src/app/services/evaluation-action-history.service';
import { QuestionModalComponent } from "../question-modal/question-modal.component";
import { DomSanitizer } from "@angular/platform-browser";
import { QuestionnaireService } from "../../services/questionnaire.service";
import { CacheService } from "../../services/cache.service";
import { Questionnaire } from 'src/app/interfaces/questionnaire.interface';
import { MatDatepicker } from '@angular/material/datepicker';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { UtilService } from 'src/app/services/general/util.service';
import { MatPaginator } from '@angular/material/paginator';
import { exportTranslations } from "../../global-constants";
import { TranslateService } from '@ngx-translate/core';

export interface Evaluacion {
  nombre: string;
  ultimaModificacion: string;
  evaluacion: string;
  evaluadores: string | null;
  history?: { fecha: string, responsable: string | null, accion: string }[];
}
export interface EvaluacionActionHistory {
  fecha: string;
  responsable: string | null;
  accion: string;
}

export const FilterFormat = {
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
      provide: MAT_DATE_FORMATS, useValue: FilterFormat
    },
  ],
  selector: 'app-lista-evaluaciones',
  templateUrl: './lista-evaluaciones.component.html',
  styleUrls: ['./lista-evaluaciones.component.scss']
})
export class ListaEvaluacionesComponent {
  displayedColumns: string[] = ['nombre', 'ultimaModificacion', 'puntuacion', 'evaluacion', 'acciones'];
  dataSource: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  filteredDataSource: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  dataSource2: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  filteredDataSource2: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  nombreRepositorio: string = '';
  selectedEvaluadores: any[] = [];
  evaluadores: string | null | undefined;
  usuarioLogado: User | null;
  cuestionario: any | null = null;
  activeFilter: string = '';
  closedFilter: string = '';
  filteredActiveRepos: string[] = [];
  filteredClosedRepos: string[] = [];
  yearDatepicker: any = null;
  selectYear: number | undefined = undefined;
  formatoSeleccionado: string = '';

  @ViewChild('activePaginator') activePaginator!: MatPaginator;
  @ViewChild('closedPaginator') closedPaginator!: MatPaginator;
  @ViewChild('picker', { static: false })
  private picker!: MatDatepicker<Date>;

  chosenYearHandler(ev: any) {
    let { _d } = ev;
    this.yearDatepicker = _d;
    this.selectYear = this.yearDatepicker?.getFullYear();
    this.applyClosedFilters();
    this.picker.close();
  }

  manualFilter() {
    if (this.yearDatepicker !== null) {
      this.selectYear = this.yearDatepicker._d.getFullYear();
    } else {
      this.selectYear = undefined;
    }
    this.applyClosedFilters();
  }

  constructor(
    private translate: TranslateService,
    private sanitizer: DomSanitizer,
    private questionnaireService: QuestionnaireService,
    private router: Router,
    private dialog: MatDialog,
    private evaluationService: EvaluationService,
    private evaluationActionHistoryService: EvaluationActionHistoryService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private cacheService: CacheService,
    private utilService: UtilService
  ) {
    this.usuarioLogado = this.authService.currentUser;
  }

  assignRepository(element: any) {
    this.router.navigate(['/asignar-usuario'], { state: { evaluacion: element } });
  }

  editEvaluadores(element: any) {
    this.router.navigate(['/asignar-usuario'], { state: { evaluacion: element } });
  }

  /** Método para obtener el historial de acciones de una evaluación
   *
   */
/*  openHistory(element: any): void {
    // Llama al servicio para obtener el historial basado en el ID de la evaluación
    const subscription: Subscription = this.evaluationActionHistoryService.findActionHistoryByIdEvaluation(element.id)
      .subscribe((history: EvaluationActionHistory[]) => {
        // Una vez que se obtiene el historial, lo pasas como data al diálogo
        this.dialog.open(HistorialAccionesComponent, {
          width: '1000px',
          data: {
            title: this.translate.instant('HISTORIAL_DE_ACCIONES'),
            content: element.nombre,
            history: history // Aquí pasas el historial obtenido
          }
        });
        // No olvides desuscribirte para evitar fugas de memoria
        subscription.unsubscribe();
      });
  }*/

  deleteClicked(evaluation: Evaluation, event: Event) {

    event.stopPropagation();

    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_ELIMINAR_CUESTIONARIO"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_CONFIRMACION_BORRAR")),
      },
      width: '50%',
      height: '60%',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        // Se hizo clic en "Aceptar" en el modal, realiza la acción deseada.
        this.evaluationService.deleteEvaluationAndQuestionnaire(evaluation).subscribe((data) => {
          this.dataSource.data = this.dataSource.data.filter((item) => item !== evaluation);
          this.dataSource2.data = this.dataSource2.data.filter((item) => item !== evaluation);

          this.filteredDataSource = this.dataSource;
          this.filteredDataSource.paginator = this.activePaginator;
          this.filteredDataSource._updateChangeSubscription();

          this.filteredDataSource2 = this.dataSource2;
          this.filteredDataSource2.paginator = this.closedPaginator;
          this.filteredDataSource2._updateChangeSubscription();
        })

      } else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });




  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  accesoEvaluacion(element: any) {
    this.router.navigate(['/cuestionario'], { state: { observation: true, evaluationId: element.id } });
  }

  closeEvaluation(element: any, index: number) {
    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_CERRAR_CUESTIONARIO"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_CERRAR_CUESTIONARIO")),
      },
      width: '50%',
      height: '60%',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.questionnaireService.closeEvaluation(element.id).subscribe();
        this.dataSource.data.splice(index, 1);
        this.filteredDataSource = this.dataSource;
        this.filteredDataSource.paginator = this.activePaginator;
        this.filteredDataSource._updateChangeSubscription();

        this.dataSource2.data.push(element);
        this.filteredDataSource2 = this.dataSource2;
        this.filteredDataSource2.paginator = this.closedPaginator;
        this.filteredDataSource2._updateChangeSubscription();
      } else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });

  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  ngOnInit() {

    this.formatoSeleccionado = 'csv';

    this.nombreRepositorio = this.route.snapshot.paramMap.get('nombreRepositorio') as string;
    const evaluadoresStr = this.route.snapshot.paramMap.get('evaluadores');
    const currentUser = this.authService.currentUser;
    const userRole = this.authService.currentUser?.rol;


    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.evaluationService.findByQuestionnaireType(idType).subscribe(data => {

        this.dataSource.data = data.filter(evaluation => evaluation.evaluationState === 'Enviado');

        this.filteredDataSource.data = this.dataSource.data;
        this.filteredDataSource.paginator = this.activePaginator;

        this.dataSource2.data = data.filter(evaluation => evaluation.evaluationState === 'Cerrado');
        this.filteredDataSource2.data = this.dataSource2.data;
        this.filteredDataSource2.paginator = this.closedPaginator;
      })
    }

  }

  isSuperEvaluatorOrAdmin(): boolean {
    return !!this.usuarioLogado && (this.usuarioLogado.rol!.includes('ADMINISTRADOR'));
  }

  isAdmin(): boolean {
    return !!this.usuarioLogado && this.usuarioLogado.rol!.includes('ADMINISTRADOR');
  }


  accesoInforme(element: any) {
    this.router.navigate(['/cuestionario'], { state: { observation: true, evaluationId: element.id } });
  }

  descargarCertificado(element: any) {
    this.questionnaireService.downloadReport(element, false).subscribe();
  }

  // Función para resetear el estado del cuestionario y la evaluación
  resetStates(evaluationId: number, index: number) {
    // Abrir el diálogo de confirmación
    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_ESTADO_INICIAL"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ESTADO_INICIAL")),
      },
      width: '50%',
      height: '60%',
    });

    // Después de que se cierra el diálogo, manejar la respuesta
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        // Si el usuario confirma, ejecutar la lógica de reseteo
        const subscription = this.questionnaireService.findByEvaluationId(evaluationId).pipe(
          switchMap(cuestionario => {
            // Aseguramos que cuestionario no sea null
            if (!cuestionario) {
              throw new Error('Cuestionario no encontrado');
            }
            // Ahora que tenemos el cuestionario, actualizamos su estado
            return this.updateQuestionnaireState(cuestionario.id, 'Enviado');
          })
        ).subscribe(
          result => {
            // Crear un nuevo arreglo con los datos actualizados

            console.log('Estados actualizados correctamente:', result);
          },
          error => {
            // Manejar el error aquí
            console.error('Error al actualizar los estados:', error);
          }
        );

        // No olvides manejar la desuscripción si es necesario.
        // Por ejemplo, podrías agregar este 'subscription' a un array de subscriptions y desuscribirte cuando el componente se destruye.
      } else {
        // El usuario canceló el diálogo
        console.log('Actualización cancelada por el usuario.');
      }
    });
  }


  // Función para actualizar el estado del cuestionario
  private updateQuestionnaireState(questionnaireId: number, newState: string): Observable<Questionnaire> {
    // Suponiendo que tenemos una forma de obtener el cuestionario por su id
    return this.questionnaireService.findById(questionnaireId).pipe(
      switchMap(questionnaire => {
        questionnaire.state = newState;
        // Solo actualizar si evaluation no es null o undefined
        if (questionnaire.evaluation) {
          questionnaire.evaluation.evaluationState = "Enviado";
        }
        return this.questionnaireService.update(questionnaire);
      })
    );
  }

  applyActiveFilters() {
    this.filteredDataSource.data = this.dataSource.data.filter(evaluation => {
      const nombreFilter = !this.activeFilter || evaluation.userRepository.repository?.institucion?.nombre?.toLowerCase().includes(this.activeFilter.toLowerCase());

      return nombreFilter;
    });
  }

  applyClosedFilters() {
    // Filtro por nombre
    this.filteredDataSource2.data = this.dataSource2.data.filter(evaluation => {
      const nombreFilter = !this.closedFilter || evaluation.userRepository.repository.institucion?.nombre.toLowerCase().includes(this.closedFilter.toLowerCase());
      return nombreFilter;
    });

    // Si existe el año, aplicar el filtro de fecha
    if (this.selectYear !== undefined && this.selectYear !== null) {
      // Crear un array de observables para las búsquedas de cuestionarios
      const observables = this.filteredDataSource2.data.map(evaluation => {
        return this.questionnaireService.findByEvaluationId(evaluation.id);
      });

      // Combinar todas las observables en un solo observable
      forkJoin(observables).subscribe((questionnaires: Questionnaire[]) => {
        // Filtrar por año y realizar las acciones necesarias
        this.filteredDataSource2.data = this.filteredDataSource2.data.filter((evaluation, index) => {
          const questionnaire = questionnaires[index];
          if (questionnaire) {
            let creationDate = questionnaire.creationDate;
            let lastEdited = evaluation.lastEdited;

            const yearFilter = this.isSameYearInRange(creationDate, lastEdited, this.selectYear!);

            return yearFilter;
          }
          return false; // Filtrar evaluaciones sin cuestionario
        });

        // Realizar acciones adicionales si es necesario
        console.log("Filtered data with year:", this.filteredDataSource2.data);
      });
    }
  }

  // Función auxiliar para verificar si el año de this.selectYear está dentro del rango de startDate y endDate
  isSameYearInRange(startDate: Date | null | undefined, endDate: Date | null | undefined, year: number): boolean {
    if (!startDate || !endDate) {
      return false; // Trataremos las fechas nulas como no coincidentes con ningún año.
    }

    const startYear = new Date(startDate).getFullYear();
    const endYear = new Date(endDate).getFullYear();

    return year >= startYear && year <= endYear;
  }

  downloadWithFilter() {
    let language = this.cacheService.getLanguage();
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (this.selectYear !== null && idType !== null) {
      this.questionnaireService.downloadAnswerFilter(idType, this.selectYear!, this.closedFilter.toLowerCase(), language, this.formatoSeleccionado)
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
