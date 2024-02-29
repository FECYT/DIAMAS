import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {QuestionnaireAnswer} from "../interfaces/questionnaire-answer";
import {Stats, StatsDivided} from "../interfaces/Stats";

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  private apiUrl = environment.host + 'stats'; // Ajusta la URL de tu API para Questionnaire

  constructor(private http: HttpClient) { }

  getGeneralStats(idType:number, fechaInicio:String,fechaFin:String): Observable<Stats> {
    return this.http.get<Stats>(this.apiUrl+`/getGeneralStats/${idType}/${fechaInicio}/${fechaFin}`);
  }

  getStatsByCategoria(idType:number, fechaInicio:String,fechaFin:String): Observable<Stats[]> {
    return this.http.get<Stats[]>(this.apiUrl+`/getStatsByCategoria/${idType}/${fechaInicio}/${fechaFin}`);
  }

  getStatsCategoriasDivided(idType:number, fechaInicio:String,fechaFin:String): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatsCategoriasDivided/${idType}/${fechaInicio}/${fechaFin}`);
  }

  getStatsDividedByQuestionnaire(idType:number, questionnaireId: number): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatsCategoriasByQuestionnaire/questionnaireId/${idType}/${questionnaireId}`);
  }

  getStatsByCategoriaAndQuestionnaireId(idType:number, questionnaireId: number): Observable<Stats[]> {
    return this.http.get<Stats[]>(this.apiUrl+`/getStatsByCategoria/questionnaireId/${idType}/${questionnaireId}`);
  }

  getStatsByRepositoryId(idType:number, id: number): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatsByRepositoryId/questionnaireId/${idType}/${id}`);
  }
  
  getStatsByEvaluationId(idType:number, id: number): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatsByEvaluationId/evaluationId/${idType}/${id}`);
  }

  getGeneralStatsByQuestionnaireId(idType:number, questionnaireId: number): Observable<Stats> {
    return this.http.get<Stats>(this.apiUrl+`/getGeneralStats/questionnaire/${idType}/${questionnaireId}`);
  }

  getStatsCategoriasByRepositoryId(idType:number, id: number,fechaInicio:String,fechaFin:String): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatByCategoria/repositoryId/${idType}/${id}/${fechaInicio}/${fechaFin}`);
  }

  getStatsByCategoriaAndRepositoryId(idType:number, id: number,fechaInicio:String,fechaFin:String): Observable<Stats[]> {
    return this.http.get<Stats[]>(this.apiUrl+`/getStatsByCategoria/repositoryId/${idType}/${id}/${fechaInicio}/${fechaFin}`);
  }

  getGeneralStatsByRepositoryId(idType:number, id: number,fechaInicio:String,fechaFin:String): Observable<Stats> {
    return this.http.get<Stats>(this.apiUrl+`/getGeneralStats/repository/${idType}/${id}/${fechaInicio}/${fechaFin}`);
  }

  getStatsByUserAndPeriodId(idType:number, periodId:number,userId:number): Observable<StatsDivided[]> {
    return this.http.get<StatsDivided[]>(this.apiUrl+`/getStatsByUserAndPeriodId/periodId/${idType}/${periodId}/userId/${userId}`);
  }


}
