import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { HistorialAccionesComponent } from '../historial-acciones/historial-acciones.component';
import { SharedDataService } from '../../services/shared-data.service';
import { EvaluationService } from 'src/app/services/evaluation.service';
import { Evaluation } from 'src/app/interfaces/evaluation.interface';
import { EvaluationWithHistory } from 'src/app/interfaces/evaluation-with-history.interface';
import { EvaluationActionHistory } from 'src/app/interfaces/evaluation-action-history.interface';
import { EvaluationActionHistoryService } from 'src/app/services/evaluation-action-history.service';
import { QuestionnaireService } from 'src/app/services/questionnaire.service';
import { Questionnaire } from 'src/app/interfaces/questionnaire.interface';
import { UtilService } from 'src/app/services/general/util.service';
import { EvaluationPeriodService } from 'src/app/services/evaluation-period.service';
import { AuthService } from '../../services/authservice.service';
import { RepositoryService } from '../../services/repository.service';
import { Repository } from '../../interfaces/repository.interface';
import { Question } from '../../interfaces/question.interface';
import { QuestionService } from '../../services/question.service';
import { QuestionnaireAnswerService } from '../../services/questionnaire-answer.service';
import { QuestionModalComponent } from "../question-modal/question-modal.component";
import { DomSanitizer } from "@angular/platform-browser";
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";
import { TranslateService } from "@ngx-translate/core";
import { QuestionnaireType } from '../../interfaces/questionnaire-type.interface';

@Component({
  selector: 'app-home-editor',
  templateUrl: './home-editor.component.html',
  styleUrls: ['./home-editor.component.scss'],
})
export class HomeEditorComponent implements OnInit {
  evaluationsWithoutCloseDate: Evaluation[] = []; // Asume que tienes una interfaz o clase 'Evaluation'
  cuestionariosWithCloseDate: Questionnaire[] = [];
  evaluationWithHistory: EvaluationWithHistory[] = [];
  preguntas: Question[] = [];
  respuestas: any = {};
  cuestionarios: Questionnaire[] = [];
  cuestionariosActivos: Questionnaire[] = [];
  nuevaEvaluacion!: Evaluation | null;
  isLoading: boolean = false;
  showEvaluacionesActivas = false
  isLoading2: boolean = false;
  showEvaluacionesPasadas = false
  public informeAccedido: boolean = false;
  displayedColumns: string[] = [];

  currentUser = this.authService.currentUser;

  repository: Repository | null = null;
  noRepo = false

  moduleId = 0

  constructor(
    private cacheService: CacheService,
    private translateService: TranslateService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private repositoryService: RepositoryService,
    private authService: AuthService,
    private dialog: MatDialog,
    private sharedDataService: SharedDataService,
    private evaluationService: EvaluationService,
    private evaluationActionHistoryService: EvaluationActionHistoryService,
    private questionnaireService: QuestionnaireService,
    private utilService: UtilService,
    private cdr: ChangeDetectorRef,
    private evaluationPeriodService: EvaluationPeriodService
  ) { }

  ngOnInit(): void {

    this.isLoading = true;
    this.isLoading2 = true;

    this.initRepository();


    this.moduleId = this.cacheService.selectedTypeId!!
    
    this.inicializarColumnas();

  }
  inicializarColumnas() {

    if (this.moduleId === 2) {
      this.displayedColumns = [
        'nombre',
        'edicion',
        'estado',
        'acciones',
      ];
    } else {
      this.displayedColumns = [
        'nombre',
        'edicion',
        'estado',
        'resultado',
        'acciones',
      ];
    }
  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  getQuestionnaires() {
    this.questionnaireService.findByUserId(this.repository!!.id!!).subscribe((data) => {

      this.cuestionarios = data.filter((cuestionario) => cuestionario.nDeleteState !== 2);

      // const cuestionariosBooks = data.filter((cuestionario) => cuestionario.evaluation!!.questionnaireType.id === 2);
      // const cuestionariosJournals = data.filter((cuestionario) => cuestionario.evaluation!!.questionnaireType.id === 1);

      // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
      // if (idType==1) this.cuestionariosActivos = cuestionariosJournals;
      // else this.cuestionariosActivos = cuestionariosBooks;
      const questionnaireType = this.cacheService.selectedTypeValue;
      if(questionnaireType !== null) {
        this.cuestionariosActivos = data.filter((cuestionario) => cuestionario.evaluation!!.questionnaireType.id === questionnaireType.id);
      }

      //Filtramos para que solo muestre los que el periodo está Abierto
      this.cuestionariosActivos = this.cuestionariosActivos.filter(
        (cuestionario) =>
          cuestionario.evaluation?.evaluationState.toLowerCase() !== 'cerrado'
      );

      let datos: Questionnaire[];
      // Adquirir también las evaluaciones pasadas del mismo usuario´
      if(questionnaireType !== null) {
        datos = data.filter((questionnaire) => questionnaire.evaluation !== null && questionnaire.evaluation.evaluationState.toLowerCase() === 'cerrado' && questionnaire.evaluation!!.questionnaireType.id === questionnaireType.id);
      } else {
        datos = data.filter((questionnaire) => questionnaire.evaluation !== null && questionnaire.evaluation.evaluationState.toLowerCase() === 'cerrado');
      }


      this.cuestionariosWithCloseDate = datos.sort((a, b) => {
        // Si ambos son null, son iguales
        if (!a.evaluation!.closeDate && !b.evaluation!.closeDate) return 0;

        // Si a es null, b debería venir primero (orden descendente)
        if (!a.evaluation!.closeDate) return 1;

        // Si b es null, a debería venir primero (orden descendente)
        if (!b.evaluation!.closeDate) return -1;

        // Finalmente, comparamos las fechas si ambas no son null
        if (a.evaluation!.closeDate > b.evaluation!.closeDate) return -1;
        if (a.evaluation!.closeDate < b.evaluation!.closeDate) return 1;

        return 0;
      });

      this.isLoading = false
      this.showEvaluacionesActivas = true
      this.isLoading2 = false;
      this.showEvaluacionesPasadas = true
    },
      (error) => {
        this.isLoading = false
        this.showEvaluacionesActivas = false
        this.isLoading2 = false;
        this.showEvaluacionesPasadas = false
        console.error('Error al obtener cuestionarios:', error);
      }
    );
  }

  initRepository() {

    this.repositoryService
      .getRepositoryByUserId(this.currentUser!!.id!!)
      .subscribe((repository) => {
        /* > 1 CUANDO ACABE PRUEBAS */

        //Si no hay repositorio dnet y es rol elevado, no será necesario inicializar repo
        if (repository == null && this.isUserElevatedRol()) {

          this.noRepo = true

          this.isLoading = false
          this.showEvaluacionesActivas = false
          this.isLoading2 = false;
          this.showEvaluacionesPasadas = false

          return
        }

        this.repository = repository;

        this.getQuestionnaires();

      });
  }

  isUserElevatedRol(): Boolean {
    return <Boolean>this.currentUser?.rol?.includes("ADMINISTRADOR")
  }

  accesoInforme(closedQuestionnaire: Questionnaire) {
    this.router.navigate(['/cuestionario'], {
      state: { closedEvaluation: closedQuestionnaire },
    });
  }
  openDialog(element: any): void {
    this.dialog.open(HistorialAccionesComponent, {
      width: '1000px',
      data: {
        title: this.translateService.instant('HISTORIAL_DE_ACCIONES'),
        content: element.nombre,
        history: element.history, // Suponiendo que cada elemento tiene un historial asociado
      },
    });
  }

  accesoCuestionario(cuestionario: any) {
    this.router.navigate(['/cuestionario'], {
      state: { cuestionario: cuestionario },
    });
  }

  /**
   * comprueba si un repositorio existe pasandole un id de dnet y checkeando en nuestra base de datos
   */
  checkExistRepositoryWithDNET() { }

  iniciarEvaluacion(): void {
    const currentDate = new Date();

    // Ejemplo de type questionnaire
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;

    /*
    let questionnaireType: QuestionnaireType = {};
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      questionnaireType = {
        id: idType
      };
    }
    */
    const questionnaireType = this.cacheService.selectedTypeValue;

    if (questionnaireType !== null) {
      const newEvaluation: Evaluation = {
        id: 0,
        userRepository: {
          repository: {
            nDeleteState: 1,
            id: this.repository?.id,
          }
        },
        lastEdited: currentDate,
        evaluationState: 'Pendiente',
        closeDate: null,
        evaluationGrade: 0,
        nDeleteState: 1,
        questionnaireType: questionnaireType
      };



      this.evaluationPeriodService.findLatestOpen(questionnaireType.id!).subscribe(
        (period) => {
          if (!period) {
            // Comprobar si period es null o indefinido
            console.error('No se encontró un período válido.');
            this.utilService.handleInfo(
              this.getExportTranslation("MENSAJE_PERIODO_NO_INICIADO")
            );

            return;
          }

          this.evaluationService.create(newEvaluation).subscribe(
            (response: Evaluation) => {
              this.nuevaEvaluacion = response;
              //Cuando cree la evaluación

              const newQuestionnaire: Questionnaire = {
                creationDate: new Date(),
                evaluation: this.nuevaEvaluacion, //inicia una nueva evaluación
                id: 0,
                flujoCount: 0,
                nDeleteState: 1,
                period: period, //el último periodo en estado abierto
                state: 'No enviado',
              };

              this.questionnaireService
                .create(newQuestionnaire)
                .subscribe((response: Questionnaire) => {
                  //console.log('Questionnaire creado con éxito:', response);
                  //this.router.navigate(['/cuestionario', response.id]); // Navega a /cuestionario/{id}
                  this.cuestionariosActivos.push(response);
                  this.cdr.detectChanges();
                });
            },
            (error) => {
              console.error('Error al crear la Evaluación:', error);
              // Aquí puedes manejar errores, como mostrar un mensaje al usuario
              this.utilService.handleError(
                this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_CREAR_EVALUACION")
              );

            }
          );
        },
        (error) => {
          console.error('Error al crear el Questionnaire:', error);
          // Aquí puedes manejar errores, como mostrar un mensaje al usuario

          this.utilService.handleError(
            this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_CREAR_CUESTIONARIO")
          );
        }
      );
    }
  }

  statsClicked(questionnaire: Questionnaire, event: Event) {
    event.stopPropagation();

    const questionnaireId = questionnaire.id;
    this.router.navigate(['/estadisticas'], { queryParams: { questionnaireId } });

  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  deleteClicked(questionnaire: Questionnaire, event: Event) {
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
        questionnaire.nDeleteState = 2
        questionnaire.evaluation!!.nDeleteState = 2
        this.questionnaireService.update(questionnaire).subscribe((data) => {
          this.cuestionariosWithCloseDate = this.cuestionariosWithCloseDate.filter((item) => item !== questionnaire);
        })

      } else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });


  }

}
