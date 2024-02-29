import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { QuestionService } from "../../services/question.service";
import { Question, QuestionDTO, TitleQuestion } from "../../interfaces/question.interface";
import { CatQuestionService } from "../../services/cat-question.service";
import { CatQuestion } from "../../interfaces/cat-question.interface";
import { Router } from "@angular/router";
import { EvaluationPeriodService } from "../../services/evaluation-period.service";
import { EvaluationPeriod } from "../../interfaces/evaluation-period.interface";
import { Title } from "vega";
import { CacheService } from 'src/app/services/cache.service';

@Component({
  selector: 'app-nueva-pregunta',
  templateUrl: './nueva-pregunta.component.html',
  styleUrls: ['./nueva-pregunta.component.scss']
})
export class NuevaPreguntaComponent implements OnInit {
  preguntaForm: FormGroup;
  evaluableControls: (AbstractControl | null)[] = [];
  catList: CatQuestion[] = [];
  period: EvaluationPeriod | null = null;
  editQuestion: Question | undefined;

  constructor(private cacheService: CacheService, private questionService: QuestionService, private catQuestionService: CatQuestionService, private evaluationPeriodService: EvaluationPeriodService, private router: Router) {
    this.preguntaForm = new FormGroup({
      tituloENG: new FormControl('', Validators.required),
      tituloESP: new FormControl('', Validators.required),
      categoria: new FormControl('', Validators.required),
      tipo: new FormControl('', Validators.required),
      peso: new FormControl('', [Validators.required, Validators.min(1), Validators.max(100)]),
      evaluable: new FormControl(false),
      textoAyuda: new FormControl(''),
      orden: new FormControl(1, [Validators.required, Validators.min(1), Validators.max(100)]),
      fileAttached: new FormControl(false),
      urlAttached: new FormControl(false),
      textoAyudaNO: new FormControl(''),
      hasNegativeExtraResponse: new FormControl(false)
    });

    this.evaluableControls[0] = this.preguntaForm.get('evaluable');
    this.evaluableControls[1] = this.preguntaForm.get('fileAttached');
    this.evaluableControls[2] = this.preguntaForm.get('urlAttached');
    this.evaluableControls[3] = this.preguntaForm.get('hasNegativeExtraResponse');

    const navigation = this.router.getCurrentNavigation();
    if (navigation && navigation.extras.state && navigation.extras.state['periodoId']) {
      const periodoId = navigation.extras.state['periodoId'];
      let editQuestion = null;
      if (navigation.extras.state['question']) editQuestion = navigation.extras.state['question'];

      if (periodoId === null) this.router.navigate(['/administrar-periodos/']);

      if (editQuestion !== null) this.editQuestion = editQuestion;

      evaluationPeriodService.findById(periodoId).subscribe((period: EvaluationPeriod) => {
        this.period = period
      })
    }
  }

  guardarPregunta() {

    console.dir('editQuestion', this.editQuestion);
    const title: TitleQuestion = {
      id: this.editQuestion?.title.id || 0, // Asegúrate de manejar el caso en el que this.editQuestion es nulo
      eng: this.preguntaForm.get('tituloENG')!.value,
      esp: this.preguntaForm.get('tituloESP')!.value
    };
    const nuevaPregunta: QuestionDTO = {
      id: 0,
      catQuestion: this.preguntaForm.get('categoria')!.value,
      title: title,
      orden: this.preguntaForm.get('orden')!.value,
      weight: this.preguntaForm.get('peso')!.value,
      helpText: this.preguntaForm.get('textoAyuda')!.value,
      isWritableByEvaluator: this.preguntaForm.get('evaluable')!.value ? 1 : 0,
      hasFileAttach: this.preguntaForm.get('fileAttached')!.value ? 1 : 0,
      hasUrlText: this.preguntaForm.get('urlAttached')!.value ? 1 : 0,
      nDeleteState: 0,
      periodId: this.period!!,
      type: this.preguntaForm.get('tipo')!.value,
      negativeMessage: this.preguntaForm.get('textoAyudaNO')!.value,
      negativeExtraPoint: 0,
      hasNegativeExtraResponse: this.preguntaForm.get('hasNegativeExtraResponse')!.value
    };

    if (this.preguntaForm.valid && this.evaluableControls[0] !== null) { // Asegurar que evaluableControl no sea nulo
      if (this.editQuestion) {
        nuevaPregunta.id = this.editQuestion.id;
        this.questionService.update(nuevaPregunta).subscribe(() => {
          this.router.navigate(['/nuevo-periodo/' + this.period!!.id])
        })
      } else {
        this.questionService.create(nuevaPregunta).subscribe(() => {
          this.router.navigate(['/nuevo-periodo/' + this.period!!.id])
        })
      }
    }
  }

  ngOnInit() {
    // Ejemplo de type questionnaire
    /*
    this.catQuestionService.findAll().subscribe((categoriasList:CatQuestion[])=>{
      this.catList = categoriasList;
      if(this.editQuestion) this.updateFormWithTermData(this.editQuestion);
    })
    */
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.catQuestionService.findByQuestionnaireType(idType).subscribe((categoriasList: CatQuestion[]) => {
        this.catList = categoriasList;
        if (this.editQuestion) this.updateFormWithTermData(this.editQuestion);
      })
    }
  }

  private updateFormWithTermData(term: Question): void {
    let selectedCategoria = this.catList.find(item => JSON.stringify(item) === JSON.stringify(this.editQuestion?.catQuestion));
    this.preguntaForm.patchValue({
      id: term.id,
      tituloENG: term.title.eng,
      tituloESP: term.title.esp,
      categoria: selectedCategoria,
      tipo: this.capitalizeFirstLetter(term.type),
      peso: term.weight,
      evaluable: term.isWritableByEvaluator,
      textoAyuda: term.helpText,
      orden: term.orden,
      fileAttached: term.hasFileAttach,
      urlAttached: term.hasUrlText,
      hasNegativeExtraResponse: term.hasNegativeExtraResponse,
      negativeMessage: term.negativeMessage
    });
  }

  private capitalizeFirstLetter(string: string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }

}
