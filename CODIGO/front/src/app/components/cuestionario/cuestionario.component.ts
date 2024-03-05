import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Pregunta } from './models/pregunta.model';
import { CuestionarioService } from './services/cuestionario.service';
import { FormGroup, AbstractControl, FormControl, Validators, ReactiveFormsModule, FormArray, FormBuilder } from '@angular/forms';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { SharedDataService } from '../../services/shared-data.service';
import { CatQuestionService } from 'src/app/services/cat-question.service';
import { CatQuestion } from 'src/app/interfaces/cat-question.interface';
import { Subject, map, concatMap, of } from 'rxjs';
import { UtilService } from 'src/app/services/general/util.service';
import { catchError, takeUntil } from 'rxjs/operators';
import { Question, QuestionDTO } from "../../interfaces/question.interface";
import { Router } from "@angular/router";
import { QuestionService } from "../../services/question.service";
import { QuestionnaireAnswerService } from "../../services/questionnaire-answer.service";
import { QuestionnaireAnswer } from "../../interfaces/questionnaire-answer";
import { Questionnaire } from "../../interfaces/questionnaire.interface";
import { QuestionnaireService } from "../../services/questionnaire.service";
import { HistorialAccionesComponent } from "../historial-acciones/historial-acciones.component";
import { QuestionModalComponent } from "../question-modal/question-modal.component";
import { FileService } from "../../services/file.service";
import { FileInterface } from "../../interfaces/file.interface";
import { delay } from 'rxjs/operators';
import { AuthService } from "../../services/authservice.service";
import { UserRepositoryService } from "../../services/user-repository.service";
import { saveAs } from 'file-saver';
import { ConfigService } from "../../services/config-service";
import { TranslateService } from "@ngx-translate/core";
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";

@Component({
  selector: 'app-cuestionario',
  templateUrl: './cuestionario.component.html',
  styleUrls: ['./cuestionario.component.scss']
})
export class CuestionarioComponent implements OnInit {
  opciones = { position1: "", position2: "No", position3: "Sí" };
  estilos = { position1: "#D3D3D3", position2: "#2b2b5e", position3: "#24a35a" };

  preguntasForm: FormGroup = this.formBuilder.group({});

  //categorias = ['POLÍTICAS', 'METADATOS', 'INTEROPERABILIDAD', 'LOGS/ESTADÍSTICAS', 'VISIBILIDAD'];
  categorias: CatQuestion[] = [];
  categoriaSeleccionada: CatQuestion = this.categorias[0];
  private destroy$ = new Subject<void>();

  usuarioTipo: ("EVALUADOR" | "SUPER EVALUADOR" | "ADMINISTRADOR" | "RESPONSABLE REPOSITORIO" | "EDITOR")[] | undefined;
  superiorRoles = ['SUPER EVALUADOR', 'ADMINISTRADOR']
  observationDisabled = true

  cuestionario: any | null = null

  preguntas: any[] = [];
  //Preguntas pulleadas del backend que se vuelcan en preguntas.
  preguntasOriginales: Question[] = [];
  //Preguntas en progreso pulleadas del backend
  preguntasProgress: QuestionnaireAnswer[] = [];

  porcentajeObligatorias = 0;
  porcentajeRecomendadas = 0;

  allQuestionsAnswered: any = {};

  nonEditable = false
  observation = false
  hideObservations = false
  hideCertificate = false;
  alertUser = false
  alertEvaluator = false
  downloadable = false
  observationsLocked = false

  delay = 200
  fileLimit = 0

  selectedCategory: any = null;
  closingQuestionnaire = false


  categories: any[] = [
    { name: 'no: ', key: '0' },
    { name: '0%: ', key: '1' },
    { name: '25%: ', key: '2' },
    { name: '50%: ', key: '3' },
    { name: '75%: ', key: '4' },
  ];
  categoriesResponses: any = []

  constructor(private cacheService: CacheService, private translateService: TranslateService, private router: Router, private configService: ConfigService, private userRepositoryService: UserRepositoryService, private authService: AuthService, private fileService: FileService, private questionnaireService: QuestionnaireService, private questionnaireAnswerService: QuestionnaireAnswerService, private questionService: QuestionService, private formBuilder: FormBuilder, private dialog: MatDialog, private sanitizer: DomSanitizer, private sharedDataService: SharedDataService, private catQuestionService: CatQuestionService, private utilService: UtilService) {

    const navigation = this.router.getCurrentNavigation();

    //Entra como evaluacion abierta
    if (navigation && navigation.extras.state && navigation.extras.state['cuestionario']) {

      const cuestionario = navigation.extras.state['cuestionario'];


      questionnaireService.findByEvaluationId(cuestionario.evaluation.id).subscribe((it => {
        this.cuestionario = it

        this.observationsLocked = true
        this.nonEditableCalc()
        this.alertUser = true
        if (this.cuestionario!!.evaluation!!.evaluationState.toLowerCase() !== "en observaciones") this.hideObservations = true

        this.ngOnInit()
      }))

    }

    //Entra como evaluación cerrada
    if (navigation && navigation.extras.state && navigation.extras.state['closedEvaluation']) {
      const closedEvaluation = navigation.extras.state['closedEvaluation']

      if (closedEvaluation.state === 'Enviado') {
        this.hideCertificate = true;
      }

      this.observationsLocked = true
      this.isEditable(false)
      this.hideObservations = true
      this.downloadable = true

      if (closedEvaluation == null) this.router.navigate(['/home-editor']);

      questionnaireService.findByEvaluationId(closedEvaluation.evaluation.id).subscribe((it => {
        this.cuestionario = it
        this.ngOnInit()
      }))
    }

    //Entra como administrador editor
    if (navigation && navigation.extras.state && navigation.extras.state['observation']) {
      const evaluationId = navigation.extras.state['evaluationId']
      this.observation = true
      this.alertEvaluator = true
      this.downloadable = true
      this.isEditable(false)

      questionnaireService.findByEvaluationId(evaluationId).subscribe((it => {
        this.cuestionario = it
        if (it.state === 'Enviado') {
          this.hideCertificate = true;
        }
        if (it.evaluation?.evaluationState === 'Cerrado') {
          this.observation = false;
        }
        this.nonEditableCalcPermission()
        this.ngOnInit()
        this.checkForPermission()
      }))
    }

  }

  ngOnInit() {

    this.categories = [
      { name: `no: ${this.translateService.instant('NEGATIVE_MESSAGE_4')}`, key: '0' },
      { name: `0%: ${this.translateService.instant('NEGATIVE_MESSAGE_0')}`, key: '1' },
      { name: `25%: ${this.translateService.instant('NEGATIVE_MESSAGE_1')}`, key: '2' },
      { name: `50%: ${this.translateService.instant('NEGATIVE_MESSAGE_2')}`, key: '3' },
      { name: `75%: ${this.translateService.instant('NEGATIVE_MESSAGE_3')}`, key: '4' },
    ];

    this.selectedCategory = this.categories[1];

    this.usuarioTipo = this.authService.currentUser!!.rol
    this.configService.getLimiteSubidaArchivos().subscribe((it) => { this.fileLimit = it })

    if (this.cuestionario != null) {
      this.loadAllCatQuestions();
      this.initForm();
    }

  }

  loadQuestionsInProgress() {
    this.questionnaireAnswerService.getByEvaluationId(this.cuestionario!!.evaluation!!.id).subscribe((answers: QuestionnaireAnswer[]) => {
      this.preguntasProgress = answers

      this.preguntasProgress.forEach(answer => {
        const idTarget = answer.questionnaireQuestion.question.id;

        let formatedAnswer = this.preguntas.find(pregunta => pregunta.id === idTarget);

        this.selectedRadioInit(idTarget, answer.negativeExtraPoint)

        formatedAnswer.documentacion = answer.file
        formatedAnswer.categoria = answer.questionnaireQuestion.question.catQuestion
        formatedAnswer.respuesta = answer.answer
        formatedAnswer.texto_URL_evidencia = answer.answerText
        formatedAnswer.observaciones = answer.observaciones
        formatedAnswer.estado = this.returnStatus(formatedAnswer)


        this.preguntas = this.preguntas.map(pregunta => pregunta.id === idTarget ? formatedAnswer : pregunta);

        //También actualizo los FormControls
        let updatedForm = {
          respuesta: answer.answer,
          texto_URL_evidencia: answer.answerText,
          observaciones: answer.observaciones
        }

        const indice = this.preguntas.findIndex(p => p.id === idTarget);

        this.setControlIndex(indice, updatedForm)
      });

      //Una vez cargado las preguntas cargo la categría inicial
      this.updatePorcentajes(this.categoriaSeleccionada)
      this.updateChipColor()
    })
  }

  selectedRadio(pregunta: any, preguntaId: number, keySelected: string) {

    let value = 0

    switch (keySelected) {
      case "0":
        value = -1
        break
      case "1":
        value = 0
        break
      case "2":
        value = 25
        break
      case "3":
        value = 50
        break;
      case "4":
        value = 75
        break;
    }

    // Buscar el índice del objeto con la misma preguntaId
    const existingIndex = this.categoriesResponses.findIndex((item: { key: number; }) => item.key === preguntaId);
    if (existingIndex !== -1) {
      // Si existe, actualizar el value
      this.categoriesResponses[existingIndex].value = value;
    } else {
      // Si no existe, agregar un nuevo objeto si la preguntaId no está en el array
      if (!this.categoriesResponses.some((item: { key: number; }) => item.key === preguntaId)) {
        const newItem = { key: preguntaId, value: value };
        this.categoriesResponses.push(newItem);
      }
    }

    this.actualizarEstado(pregunta)
  }

  selectedRadioInit(preguntaId: number, valueSelected: number) {

    const newItem = { key: preguntaId, value: 0 };
    let index = 0

    switch (valueSelected) {
      case -1:
        newItem.value = -1
        index = 0
        break
      case 0:
        newItem.value = 0
        index = 1
        break
      case 25:
        newItem.value = 25
        index = 2
        break
      case 50:
        newItem.value = 50
        index = 3
        break
      case 75:
        newItem.value = 75
        index = 4
        break
    }

    if (valueSelected == null) return;

    this.categoriesResponses.push(newItem);
    this.findPreguntaById(preguntaId).selectedCategory = this.categories[index];
  }

  findPreguntaById(id: number): any | undefined {
    return this.preguntas.find(pregunta => pregunta.id === id);
  }

  nonEditableCalc() {

    const evaluationStatus = this.cuestionario!!.evaluation!!.evaluationState.toLowerCase()
    const cuestionarioStatus = this.cuestionario!!.state.toLowerCase()

    if (evaluationStatus === "cerrado") this.isEditable(false)
    else if (evaluationStatus === "pendiente" && cuestionarioStatus === "en proceso") this.isEditable(false)
    else if (evaluationStatus === "en proceso" && cuestionarioStatus === "enviado") this.isEditable(false)
    else if (evaluationStatus === "pendiente" && cuestionarioStatus === "enviado") this.isEditable(false)
    else if (evaluationStatus === "pendiente" && cuestionarioStatus === "no enviado") this.isEditable(true)
    else if (evaluationStatus === "en observaciones" && cuestionarioStatus === "enviado") this.isEditable(true)

  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  nonEditableCalcPermission() {

    const evaluationStatus = this.cuestionario!!.evaluation!!.evaluationState.toLowerCase()
    const cuestionarioStatus = this.cuestionario!!.state.toLowerCase()

    //Si sabemos que solo es responsable no lanzamos la query para chekear si es el owner del repositorio y así evitar que edite su observable

    this.userRepositoryService.doesUserOwnRepository(this.authService.currentUser!!.id as number, this.cuestionario!!.period!!.id as number).subscribe((response) => {
      if (response) this.observationDisabled = true
      else {
        if (evaluationStatus === "cerrado") this.observationDisabled = false
        else if (evaluationStatus === "pendiente" && cuestionarioStatus === "no enviado") this.observationDisabled = true
        else if (evaluationStatus === "en proceso" && cuestionarioStatus === "enviado") this.observationDisabled = false
        else this.observationDisabled = true
      }
    })


  }

  isEditable(bool: Boolean) {
    if (bool) {
      this.nonEditable = false
    } else {
      this.nonEditable = true
      this.preguntasForm.disable()
    }
  }


  loadAllCatQuestions(): void {
    // Ejemplo de type questionnaire
    /*
    this.catQuestionService.findAll()
    .pipe(
      map((data: any[]) => data.filter(cat => cat.ndeleteState === 1)),
      takeUntil(this.destroy$)
    )
    .subscribe(
      data => {
        this.categorias = data;

        // Ordenar dataSource por el campo "orden" de manera ascendente
        this.categorias.sort((a, b) => a.orden - b.orden);
        // Aquí, después de cargar y ordenar las categorías, estableces la primera como seleccionada
        if (this.categorias.length > 0) {
          this.categoriaSeleccionada = this.categorias[0];
        }
      },
      error => {
        console.error('Error al cargar las categorías', error);
        this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT")+this.getExportTranslation("ERROR_CARGAR_CATEGORIAS"));
      }
    );
    */
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.catQuestionService.findByQuestionnaireType(idType).pipe(
        map((data: any[]) => data.filter(cat => cat.ndeleteState === 1)),
        takeUntil(this.destroy$)
      ).subscribe(
        data => {
          this.categorias = data;

          // Ordenar dataSource por el campo "orden" de manera ascendente
          this.categorias.sort((a, b) => a.orden - b.orden);
          // Aquí, después de cargar y ordenar las categorías, estableces la primera como seleccionada
          if (this.categorias.length > 0) {
            this.categoriaSeleccionada = this.categorias[0];
          }
        },
        error => {
          console.error('Error al cargar las categorías', error);
          this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_CARGAR_CATEGORIAS"));
        }
      );
    }
  }

  downloadFile(pregunta: any) {
    this.fileService.download(pregunta.documentacion.id).subscribe(blob => {
      // Guardar el blob como archivo local
      saveAs(blob, pregunta.documentacion.normalizedDocumentName);
    });
  }

  checkForPermission() {
    //Si el usuario entrante tiene rango superior, se le deja entrar. Si es un simple evaluador, se comprueba que tenga acceso
    if (this.usuarioTipo && !this.usuarioTipo.some(userType => this.superiorRoles.includes(userType.toUpperCase()))) {
      // @ts-ignore
      if (!this.cuestionario?.evaluation.users.some(user => user.id === this.authService.currentUser!!.id)) this.returnLastPage()
    }
  }

  returnTypeQuestion(pregunta: any) {
    const literal = this.translateService.instant(pregunta.tipo.toUpperCase())
    return "(" + literal + ")"
  }

  initForm(): void {
    this.questionService.getQuestionsByPeriodId(this.cuestionario!!.period!!.id).subscribe((questions: Question[]) => {

      this.preguntasOriginales = questions;

      questions.forEach((question: Question) => {

        const newQuestion: any = {
          id: question.id,
          orden: question.orden,
          texto: question.title,
          ayuda: question.helpText,
          tipo: question.type,
          categoria: question.catQuestion,
          respuesta: null,
          texto_URL_evidencia: "",
          estado: "Pendiente",
          observaciones: "",
          documentacion: null,
          hasUrlText: question.hasUrlText,
          hasFileAttach: question.hasFileAttach,
          negativeMessage: question.negativeMessage,
          hasNegativeExtraResponse: question.hasNegativeExtraResponse
        };

        this.preguntas.push(newQuestion)
      })


      const preguntasArray = this.preguntas.map(pregunta => {
        return this.formBuilder.group({
          respuesta: [pregunta.respuesta],  // Inicialmente, las respuestas pueden estar vacías
          texto_URL_evidencia: [pregunta.texto_URL_evidencia],
          observaciones: new FormControl("")
        })
      })

      this.preguntasForm = this.formBuilder.group({
        preguntas: this.formBuilder.array(preguntasArray)
      });


      // Para este punto ya tengo las preguntasProgress y preguntas cargadas
      // Ahora sustituyo en preguntas las que tengo cargadas buscandolo por su campo id que coincide con el id del question que buscaremos en preguntasprogress

      this.loadQuestionsInProgress()

    });

  }

  getLanguagedRadioButtonText(categoria: any): string {

    const sub = categoria.name.substring(0, 2)


    switch (sub) {
      case "no":
        return ` ${this.translateService.instant('NEGATIVE_MESSAGE_4')}`
      case "0%":
        return `0%: ${this.translateService.instant('NEGATIVE_MESSAGE_0')}`
      case "25":
        return `25%: ${this.translateService.instant('NEGATIVE_MESSAGE_1')}`
      case "50":
        return `50%: ${this.translateService.instant('NEGATIVE_MESSAGE_2')}`
      case "75":
        return `75%: ${this.translateService.instant('NEGATIVE_MESSAGE_3')}`
      default:
        return this.translateService.instant('Error')
    }

  }


  // @ts-ignore
  returnStatus(pregunta: any): string {

    switch (pregunta.respuesta) {

      case true:
        return "Contestada"

      case false:
        if (!pregunta.hasNegativeExtraResponse) return "Contestada";
        else if (pregunta.hasNegativeExtraResponse && this.isStatusValid(pregunta)) {
          return "Contestada";
        } else return "Pendiente"
    }

  }

  get preguntasArray(): FormArray {
    return this.preguntasForm.get('preguntas') as FormArray;
  }

  getRespuestaControl(index: number): FormControl {
    return (this.preguntasForm.get('preguntas') as FormArray).at(index).get('respuesta') as FormControl;
  }

  getUrlControl(index: number): FormControl {
    return (this.preguntasForm.get('preguntas') as FormArray).at(index).get('texto_URL_evidencia') as FormControl;
  }

  getObservationControl(index: number): any {
    if (this.hideObservations) return new FormControl();
    return (this.preguntasForm.get('preguntas') as FormArray).at(index).get('observaciones') as FormControl;
  }

  //Solo acepta parámetros indexados en la variable preguntas
  getControlIndex(index: number) {
    const preguntasArray = this.preguntasForm.get('preguntas') as FormArray;
    const preguntaFormGroup = preguntasArray.at(index);

    return preguntaFormGroup.value
  }

  setControlIndex(index: number, newValue: any) {
    const preguntasArray = this.preguntasForm.get('preguntas') as FormArray;
    const preguntaFormGroup = preguntasArray.at(index) as FormGroup;

    preguntaFormGroup.setValue(newValue);
  }

  crearPreguntaForm(pregunta: Pregunta): FormGroup {
    return new FormGroup({
      respuesta: new FormControl(pregunta.respuesta),
      texto_URL_evidencia: new FormControl(pregunta.texto_URL_evidencia),
      estado: new FormControl(pregunta.estado),
      observaciones: new FormControl(pregunta.observaciones),
      documentacion: new FormControl(pregunta.documentacion || undefined)
    });
  }

  onFileChange(event: any, pregunta: any): void {

    const preguntaInProgress = this.preguntasProgress.find(it => it.questionnaireQuestion.question.id === pregunta.id)

    if (preguntaInProgress == undefined) {

      const indice = this.preguntas.findIndex(p => p.id === pregunta.id);
      const formRespuestas = this.getControlIndex(indice)

      const creatingAnswer: QuestionnaireAnswer = {
        id: 0,
        questionnaireQuestion: {
          id: 0,
          questionnaire: this.cuestionario!!,
          question: this.preguntasOriginales.find(preguntaOriginal => preguntaOriginal.id === pregunta.id)!!,
          nDeleteState: 0
        },
        observaciones: formRespuestas.observaciones,
        answerText: formRespuestas.texto_URL_evidencia,
        answer: pregunta.respuesta,
        file: pregunta.documentacion,
        nDeleteState: 1,
        negativeExtraPoint: this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value
      }

      this.questionnaireAnswerService.interactedAnswer(this.cuestionario.id, creatingAnswer).subscribe((respuesta: any) => {
        this.preguntasProgress.push(respuesta)
        this.createFile(event, pregunta)
      })

    } else {
      this.createFile(event, pregunta)
    }

  }

  formatearTexto(texto: string) {
    const delimitadores = [':', ';', '.'];
    let indiceDelimitador = texto.length;

    // Buscar el primer delimitador
    for (const delimitador of delimitadores) {
      const indice = texto.indexOf(delimitador);
      if (indice !== -1 && indice < indiceDelimitador) {
        indiceDelimitador = indice;
      }
    }

    // Extraer la parte en negrita
    let parteNegrita;
    if (indiceDelimitador !== texto.length) {
      parteNegrita = texto.substring(0, indiceDelimitador + 1);
    } else {
      // Si no se encuentra un delimitador, toda la cadena es la parte en negrita
      parteNegrita = texto;
    }

    // El resto del texto
    const resto = texto.substring(indiceDelimitador + 1);

    return { parteNegrita, resto };
  }

  getLanguagedText(pregunta: any) {

    switch (this.cacheService.getLanguage().toLowerCase()) {
      case "es":
        return pregunta.texto.esp
      default:
        return pregunta.texto.eng
    }

  }

  handlePositionChange(event: any, pregunta: any) {

    if (this.nonEditable) return;

    const indice = this.preguntas.findIndex(p => p.id === pregunta.id);
    const formRespuestas = this.getControlIndex(indice)

    switch (event) {

      case "position1":

        this.categoriesResponses = this.categoriesResponses.filter(
          (item: { key: any; }) => item.key !== pregunta.id
        );
        pregunta.selectedCategory = null

        pregunta.respuesta = null
        formRespuestas.respuesta = null
        break;
      case "position2":
        pregunta.respuesta = false
        formRespuestas.respuesta = false
        break;
      case "position3":

        this.categoriesResponses = this.categoriesResponses.filter(
          (item: { key: any; }) => item.key !== pregunta.id
        );
        pregunta.selectedCategory = null

        pregunta.respuesta = true
        formRespuestas.respuesta = true
        break;

    }

    this.actualizarEstado(pregunta)

    this.delayTimeout()

  }

  delayCoroutine() {
    return new Promise<void>((resolve) => {
      setTimeout(() => {
        // Oculta la cortinilla después del retraso
        //this.launchDelay = false;
        resolve();
      }, this.delay);
    });
  }

  async delayTimeout() {
    //this.launchDelay = true
    await this.delayCoroutine()
  }

  createFile(event: any, pregunta: any) {
    const MAX_SIZE = this.fileLimit * 1024 * 1024;
    const file = event.target.files[0];

    if (file.size > MAX_SIZE) {
      this.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("MENSAJE_ERROR_SUBIDA_ARCHIVO_CUESTIONARIO_TAMAÑO"));
      return;
    }

    const name = event.target.files[0].name
    const extension = name.split('.').pop().toLowerCase();

    if (extension === 'pdf' || extension === 'jpg' || extension === 'png') {

      if (event.target.files.length > 0) {

        Array.from(event.target.files).forEach((file: any) => {

          this.uploadFile(file, pregunta.id).subscribe((filevo: FileInterface) => {
            pregunta.documentacion = filevo
            this.actualizarEstado(pregunta)
          })

        });

      }

    } else {
      this.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("MENSAJE_ERROR_SUBIDA_ARCHIVO_CUESTIONARIO"));
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();  // Emite un valor, lo que hace que cualquier observador que utilice takeUntil(this.destroy$) se complete
    this.destroy$.complete();  // Marca el subject como completado
  }

  envioCuestionario() {

    if (this.preguntasForm.valid) {

      const hayPendientes: Boolean = this.preguntas.some((pregunta) => pregunta.estado.toLowerCase() === "pendiente");

      if (hayPendientes) {
        this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT_CUESTIONARIO") + this.getExportTranslation("ERROR_CUESTIONARIO_INCOMPLETO"));
      } else {

        const dialogRef = this.dialog.open(QuestionModalComponent, {
          data: {
            title: this.getExportTranslation("CONFIRMACION_ENVIAR_CUESTIONARIO"),
            content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ENVIAR_CUESTIONARIO")),
          },
          width: '50%',
          height: '60%',
        });

        dialogRef.afterClosed().subscribe((result: boolean) => {
          if (result) {
            // Se hizo clic en "Aceptar" en el modal, realiza la acción deseada.

            // En lugar de redireccionar, descargar el certificado

            this.closingQuestionnaire = true

            this.questionnaireService.closeEvaluation(this.cuestionario.evaluation.id).subscribe((data) => {

              this.questionnaireService.downloadReport(this.cuestionario.evaluation, true).subscribe(()=>{

                this.closingQuestionnaire = false
                this.router.navigate(['/home-editor']);

              })

            })

          } else {
            // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
          }
        });

      }
    }

  }

  envioYCierre() {

    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_CERRAR_CUESTIONARIO"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ENVIAR_CUESTIONARIO_OBSERVACIONES")),
      },
      width: '50%',
      height: '60%',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {

      if (result) {
        // Se hizo clic en "Aceptar" en el modal, realiza la acción deseada.
        this.questionnaireService.closeEvaluation(this.cuestionario.evaluation.id).subscribe(() => {
          this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_CONTENT_CUESTIONARIO"))
          this.router.navigate(['/listaEvaluacionesActiva']);
        })
      }

      else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });

  }

  envioCuestionarioObservaciones() {

    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_ENVIAR_CUESTIONARIO"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ENVIAR_CUESTIONARIO_OBSERVACIONES")),
      },
      width: '50%',
      height: '60%',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        // Se hizo clic en "Aceptar" en el modal, realiza la acción deseada.
        this.questionnaireService.statusToEnObservacion(this.cuestionario!!.id).subscribe(() => {
          this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_CONTENT_CUESTIONARIO"))
          this.router.navigate(['/listaEvaluacionesActiva']);
        })
      }

      else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });

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
      }, 1000);
    });
  }


  guardarRespuestas(): void {
    this.questionnaireAnswerService.saveAnswersList(this.preguntasProgress).subscribe(() => {
      this.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("MENSAJE_PROGRESO_GUARDADO"));
    })
  }

// Inicializar updateStatusTimer como un Map para almacenar temporizadores por pregunta.id
  private updateStatusTimer: Map<number, ReturnType<typeof setTimeout>> = new Map();

  actualizarEstado(pregunta: any): void {
    const indice = this.preguntas.findIndex(p => p.id === pregunta.id);
    const formRespuestas = this.getControlIndex(indice)

    // Cancelar el temporizador existente para esta pregunta si hay uno
    if (this.updateStatusTimer.has(pregunta.id)) {
      clearTimeout(this.updateStatusTimer.get(pregunta.id));
    }

    // Iniciar un nuevo temporizador para esta pregunta
    const timerId = setTimeout(() => {
      this.updateQuestionStatus(pregunta, formRespuestas);
      this.updatePorcentajes(this.categoriaSeleccionada);
      this.updateChipColor();

      // Limpiar el temporizador del mapa después de ejecutar
      this.updateStatusTimer.delete(pregunta.id);
    }, 500); // 500 milisegundos = 0.5 segundos

    // Almacenar el identificador del temporizador en el mapa con pregunta.id como clave
    this.updateStatusTimer.set(pregunta.id, timerId);

    this.updateChipColor();
  }


  returnLastPage() {
    if (this.observation) {
      this.router.navigate(['/listaEvaluacionesActiva']);
    } else {
      this.router.navigate(['/home-editor']);
    }
  }

  getCurrentPos(pregunta: any) {

    switch (pregunta.respuesta) {
      case null:
        return "position1"
      case false:
        return "position2"
      case true:
        return "position3"
      default:
        return "position1"
    }

  }

  clearFile(pregunta: any) {

    pregunta.documentacion.nDeleteState = 2
    this.fileService.update(pregunta.documentacion).subscribe((it: any) => {
      //Por algún motivo si quito este suscribe el endpoint no funciona como se espera
      //console.log("Actualizado con exito")
    })

    pregunta.documentacion = null
    this.actualizarEstado(pregunta)

  }

  updateChipColor() {

    this.categorias.forEach((categoria) => {

      const total = this.preguntas.filter((pregunta) => {
        return pregunta.categoria.categoryType?.toLowerCase() === categoria.categoryType?.toLowerCase();
      });

      const contestadas = this.preguntas.filter((pregunta) => {
        return pregunta.categoria.categoryType?.toLowerCase() === categoria.categoryType?.toLowerCase() && pregunta.estado?.toLowerCase() === "contestada";
      });

      this.allQuestionsAnswered[categoria.categoryType!!.toLowerCase()] = contestadas.length == total.length;

    })



  }

  updateQuestionStatus(pregunta: any, formRespuestas: any) {

    const answer: QuestionnaireAnswer = {
      id: 0,
      questionnaireQuestion: {
        id: 0,
        questionnaire: this.cuestionario!!,
        question: this.preguntasOriginales.find(preguntaOriginal => preguntaOriginal.id === pregunta.id)!!,
        nDeleteState: 1
      },
      observaciones: formRespuestas.observaciones,
      answerText: formRespuestas.texto_URL_evidencia,
      answer: pregunta.respuesta,
      file: pregunta.documentacion,
      nDeleteState: 1,
      negativeExtraPoint: this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value

    }

    this.interactedAnswer(answer)

    if (pregunta.hasUrlText === 0 && pregunta.hasFileAttach === 0) {

      pregunta.respuesta = formRespuestas.respuesta;

      if (pregunta.respuesta == null) {
        pregunta.estado = "Pendiente"
        return;
      }

      if (pregunta.respuesta) {
        pregunta.estado = "Contestada"
        return;
      }

      if (!pregunta.hasNegativeExtraResponse) pregunta.estado = "Contestada";
      else if (pregunta.hasNegativeExtraResponse && this.isStatusValid(pregunta)) {
        pregunta.estado = "Contestada";
      } else pregunta.estado = "Pendiente";


    } else if (pregunta.hasUrlText === 1 && pregunta.hasFileAttach === 0) {

      pregunta.respuesta = formRespuestas.respuesta;

      if (pregunta.respuesta == null) {
        pregunta.estado = "Pendiente"
        return;
      }

      if (pregunta.respuesta) {
        pregunta.estado = "Contestada"
        return;
      }

      if (!pregunta.hasNegativeExtraResponse) pregunta.estado = "Contestada";
      else if (pregunta.hasNegativeExtraResponse && this.isStatusValid(pregunta)) {
        pregunta.estado = "Contestada";
      } else pregunta.estado = "Pendiente";


    } else if (pregunta.hasUrlText === 0 && pregunta.hasFileAttach === 1) {

      pregunta.respuesta = formRespuestas.respuesta;

      if (pregunta.respuesta == null) {
        pregunta.estado = "Pendiente"
        return;
      }

      if (pregunta.respuesta) {
        pregunta.estado = "Contestada"
        return;
      }

      if (!pregunta.hasNegativeExtraResponse) pregunta.estado = "Contestada";
      else if (pregunta.hasNegativeExtraResponse && this.isStatusValid(pregunta)) {
        pregunta.estado = "Contestada";
      } else pregunta.estado = "Pendiente";


    } else if (pregunta.hasUrlText === 1 && pregunta.hasFileAttach === 1) {

      pregunta.respuesta = formRespuestas.respuesta;

      if (pregunta.respuesta == null) {
        pregunta.estado = "Pendiente"
        return;
      }

      if (pregunta.respuesta) {
        pregunta.estado = "Contestada"
        return;
      }

      if (!pregunta.hasNegativeExtraResponse) pregunta.estado = "Contestada";
      else if (pregunta.hasNegativeExtraResponse && this.isStatusValid(pregunta)) {
        pregunta.estado = "Contestada";
      } else pregunta.estado = "Pendiente";

    }

  }

  isStatusValid(pregunta: any) {
    return this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value != null && this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value != undefined
  }

  interactedAnswer(pregunta: any) {
    this.questionnaireAnswerService.interactedAnswer(this.cuestionario.id, pregunta).subscribe((respuesta: any) => {

      if (respuesta.answer != pregunta.answer) {
        pregunta.answer = null
        this.updateChipColor()
        return;
      }  //TODO EVALUADOR

      this.preguntasProgress.push(respuesta);
      this.cuestionario.evaluation = respuesta.questionnaireQuestion.questionnaire.evaluation;
    })
  }

  textAreaLoseFocus(readOnly: boolean, event: any, pregunta: any) {
    //Trato de actualizarlo en cuanto pierda focus para que no envie peticiones HTTP sin parar si lo actualizo en cuanto se actualida el textfield
    if (!readOnly) {
      this.actualizarEstado(pregunta)
    }
  }

  updatedTextArea(pregunta: any) {
    const indice = this.preguntas.findIndex(p => p.id === pregunta.id);
    const formRespuestas = this.getControlIndex(indice)

    pregunta.answerText = formRespuestas.texto_URL_evidencia
    pregunta.observaciones = formRespuestas.observaciones

  }

  // Puedes tener una propiedad que almacene el índice actual en el componente
  indiceCategoriaSeleccionada: number = 0;  // inicializado en 0 para el primer elemento

  // Al seleccionar una categoría, debes actualizar este índice
  seleccionarCategoria(categoria: any): void {
    this.indiceCategoriaSeleccionada = this.categorias.indexOf(categoria);
    this.categoriaSeleccionada = categoria;

    this.updatePorcentajes(categoria)
  }

  cambiarASiguienteCategoria(): void {
    if (this.indiceCategoriaSeleccionada < this.categorias.length - 1) {
      // Si no es la última categoría
      this.indiceCategoriaSeleccionada++;
      this.categoriaSeleccionada = this.categorias[this.indiceCategoriaSeleccionada];
      this.updatePorcentajes(this.categoriaSeleccionada)
    }
    window.scrollTo({ top: 400, behavior: 'smooth' });
  }

  cambiarACategoriaAnterior(): void {
    if (this.indiceCategoriaSeleccionada > 0) {
      this.indiceCategoriaSeleccionada--;
      this.categoriaSeleccionada = this.categorias[this.indiceCategoriaSeleccionada];
      this.updatePorcentajes(this.categoriaSeleccionada)
    }
    window.scrollTo({ top: 400, behavior: 'smooth' });
  }

  updatePorcentajes(categoria: any) {

    //categorias = ['POLÍTICAS', 'METADATOS', 'INTEROPERABILIDAD', 'LOGS/ESTADÍSTICAS', 'VISIBILIDAD'];
    this.porcentajeObligatorias = 0
    this.porcentajeRecomendadas = 0

    const filteredObligatorias = this.preguntas.filter((pregunta) => {
      return pregunta.tipo === "advanced" && pregunta.categoria.categoryType.toLowerCase() === categoria.categoryType.toLowerCase() && pregunta.estado.toLowerCase() === "contestada";
    });

    const totalObligatorias = this.preguntas.filter((pregunta) => {
      return pregunta.tipo === "advanced" && pregunta.categoria.categoryType.toLowerCase() === categoria.categoryType.toLowerCase();
    });


    const filteredRecomendadas = this.preguntas.filter((pregunta) => {
      return pregunta.tipo === "basic" && pregunta.categoria.categoryType.toLowerCase() === categoria.categoryType.toLowerCase() && pregunta.estado.toLowerCase() === "contestada";
    });

    const totalRecomendadas = this.preguntas.filter((pregunta) => {
      return pregunta.tipo === "basic" && pregunta.categoria.categoryType.toLowerCase() === categoria.categoryType.toLowerCase();
    });

    let totalWeightObligatorias = totalObligatorias.reduce((total, pregunta) => {
      const preguntaOriginal = this.preguntasOriginales.find((preguntaOrig) => pregunta.id === preguntaOrig.id);
      if (preguntaOriginal) {
        return total + preguntaOriginal.weight;
      }
      return total;
    }, 0);


    let totalWeightRecomendadas = totalRecomendadas.reduce((total, pregunta) => {
      const preguntaOriginal = this.preguntasOriginales.find((preguntaOrig) => pregunta.id === preguntaOrig.id);
      if (preguntaOriginal) {
        return total + preguntaOriginal.weight;
      }
      return total;
    }, 0);


    let totalWeight = totalWeightRecomendadas + totalWeightObligatorias

    //Quitamos las no evaluables
    filteredObligatorias.forEach((pregunta) => {
      const preguntaOriginal = this.preguntasOriginales.find((preguntaOrig) => pregunta.id === preguntaOrig.id);
      const answer = this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value
      if (answer === -1) totalWeight -= preguntaOriginal!!.weight
    });

    filteredRecomendadas.forEach((pregunta) => {
      const preguntaOriginal = this.preguntasOriginales.find((preguntaOrig) => pregunta.id === preguntaOrig.id);
      const answer = this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value
      if (answer === -1) totalWeight -= preguntaOriginal!!.weight
    });
    //

    filteredObligatorias.forEach((pregunta) => {

      const answer = this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value
      if (answer == -1) return;

      const preguntaOriginal = this.preguntasOriginales.find((preguntaOrig) => pregunta.id === preguntaOrig.id);
      if (preguntaOriginal) {

        if (pregunta.respuesta == true) {
          const porcentajeIndividual = (preguntaOriginal.weight / totalWeight) * 100;
          this.porcentajeObligatorias += porcentajeIndividual
        } else if (pregunta.respuesta == false && pregunta.selectedCategory != undefined) {
          const porcentajeIndividual = (preguntaOriginal.weight * this.selectedCategoryWeight(pregunta.selectedCategory.key) / totalWeight) * 100;
          this.porcentajeObligatorias += porcentajeIndividual
        }

      }
    });

    filteredRecomendadas.forEach((pregunta) => {

      const answer = this.categoriesResponses.find((item: { key: number; }) => item.key === pregunta.id)?.value
      if (answer == -1) return;

      const preguntaOriginal = this.preguntasOriginales.find((it) => pregunta.id === it.id);
      if (preguntaOriginal) {

        if (pregunta.respuesta == true) {
          const porcentajeIndividual = (preguntaOriginal.weight / totalWeight) * 100;
          this.porcentajeObligatorias += porcentajeIndividual
        } else if (pregunta.respuesta == false && pregunta.selectedCategory != undefined) {
          const porcentajeIndividual = (preguntaOriginal.weight * this.selectedCategoryWeight(pregunta.selectedCategory.key) / totalWeight) * 100;
          this.porcentajeObligatorias += porcentajeIndividual
        }
      }
    });

  }

  selectedCategoryWeight(key: string): number {
    switch (key) {
      case "0":
        return 0;
      case "1":
        return 0;
      case "2":
        return 0.25;
      case "3":
        return 0.50;
      case "4":
        return 0.75;
      default: return 0;
    }
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


  uploadFile(file: File, preguntaId: number) {
    const formData = new FormData();

    formData.append(
      'file',
      new Blob([JSON.stringify(file)], {
        type: 'application/json',
      })
    );

    // Agregar el archivo al FormData
    formData.append("fichero", file);

    return this.fileService.create(formData, this.cuestionario!!.id, preguntaId)
  }

  descargarCertificado() {
    this.questionnaireService.downloadReport(this.cuestionario.evaluation, false).subscribe();
  }




}

