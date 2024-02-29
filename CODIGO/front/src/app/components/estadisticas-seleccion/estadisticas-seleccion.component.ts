import { Component } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { RepositoryService } from "../../services/repository.service";
import { Repository } from "../../interfaces/repository.interface";
import { QuestionnaireService } from "../../services/questionnaire.service";
import { Questionnaire } from "../../interfaces/questionnaire.interface";
import { UserRepositoryService } from 'src/app/services/user-repository.service';
import { EvaluationService } from 'src/app/services/evaluation.service';
import { UserEvaluationDTO } from 'src/app/interfaces/userEvaluationDTO.interface';
import { UserInstitutionDTO } from 'src/app/interfaces/userInstitutionDTO.interface';
import { Evaluation } from 'src/app/interfaces/evaluation.interface';
import { CacheService } from 'src/app/services/cache.service';
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';

@Component({
  selector: 'app-estadisticas-seleccion',
  templateUrl: './estadisticas-seleccion.component.html',
  styleUrls: ['./estadisticas-seleccion.component.scss']
})
export class EstadisticasSeleccionComponent {

  seleccionRepos = false
  allData: Questionnaire[] = []
  cuestionarioList: Questionnaire[] = []
  cuestionarioListFiltered: Questionnaire[] = []
  searchingTerm = ""
  institutionList: Questionnaire[] = []
  institutionListFiltered: Questionnaire[] = []
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];
  constructor(public cacheService: CacheService, private route: ActivatedRoute, private repositoryService: RepositoryService, private router: Router, private cuestionarioService: QuestionnaireService, private userRepositoryService: UserRepositoryService, private evaluationService: EvaluationService, private questionnaireService: QuestionnaireService) { }

  ngOnInit(): void {
    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }

    this.route.queryParams.subscribe(params => {
      console.dir('params', params['s']);
      const a = params['s'];
      if (a == "cuestionarios") this.seleccionRepos = true
      else this.seleccionRepos = false
    });

    /*
    if (this.seleccionRepos) this.initRepos()
    else this.initCuestionarios()
  */
    this.initData();

  }

  initData() {
    this.questionnaireService.findAll().subscribe((data) => {
      this.allData = data;
      if (this.selectedType !== null) {
        this.institutionList = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
        this.institutionListFiltered = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
        this.cuestionarioList = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
        this.cuestionarioListFiltered = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
      } else {
        this.institutionList = data;
        this.institutionListFiltered = data;
        this.cuestionarioList = data;
        this.cuestionarioListFiltered = data;
      }
    });
  }

  repoClicked(cuestionario: any) {
    const questionnaireId = cuestionario.id;
    this.router.navigate(['/estadisticas'], { queryParams: { questionnaireId } })
  }

  cuestionarioClicked(cuestionario: any) {
    const questionnaireId = cuestionario.id;
    this.router.navigate(['/estadisticas'], { queryParams: { questionnaireId } })
  }

  /*
  initCuestionarios() {
    this.questionnaireService.findAll().subscribe((data) => {
      const questionnaireType = this.cacheService.selectedTypeValue;
      if (questionnaireType !== null) {
        this.institutionList = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === questionnaireType.id);
        this.institutionListFiltered = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === questionnaireType.id);
      } else {
        this.institutionList = data
        this.institutionListFiltered = data
      }
    });
  }
  */

  reFilterListRepos() {

    if (this.searchingTerm == "") {
      this.cuestionarioListFiltered = this.cuestionarioList;
      return
    }

    this.cuestionarioListFiltered = this.cuestionarioList.filter((repo: Questionnaire) => {
      const fullName = `${repo.evaluation!.userRepository.user!.nombre} ${repo.evaluation!.userRepository.user!.apellidos}`.toLowerCase();
      return fullName.includes(this.searchingTerm.toLowerCase());
    });


  }

  reFilterList() {
    if (this.seleccionRepos) this.reFilterListRepos()
    else this.reFilterListCuestionarios()
  }

  elementClicked(element: any) {
    if (this.seleccionRepos) this.repoClicked(element)
    else this.cuestionarioClicked(element)
  }

  reFilterListCuestionarios() {
    if (this.searchingTerm == "") {
      this.institutionListFiltered = this.institutionList;
      return
    }

    this.institutionListFiltered = this.institutionList.filter((cuestionario: Questionnaire) => {
      const institucion = cuestionario.evaluation!.userRepository.repository.institucion!.nombre.toLowerCase();
      return institucion.includes(this.searchingTerm.toLowerCase());
    });
  }

  onFieldsChange() {
    this.cacheService.selectedTypeValue = this.selectedType;
    if (this.selectedType !== null) {
      const data = this.allData;
      this.institutionList = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
      this.institutionListFiltered = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
      this.cuestionarioList = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
      this.cuestionarioListFiltered = data.filter((questionnaire) => questionnaire.evaluation!!.questionnaireType.id === this.selectedType!.id);
    }
  }

}
