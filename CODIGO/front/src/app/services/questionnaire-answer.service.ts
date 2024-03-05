import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Questionnaire} from "../interfaces/questionnaire.interface";
import {Observable} from "rxjs";
import {QuestionnaireAnswer} from "../interfaces/questionnaire-answer";

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireAnswerService {
  private apiUrl = environment.host + 'api/questionnaireAnswer/'; // Ajusta la URL de tu API para Questionnaire

  constructor(private http: HttpClient) { }

  create(questionnaire: QuestionnaireAnswer): Observable<QuestionnaireAnswer> {
    return this.http.post<QuestionnaireAnswer>(this.apiUrl+'question', questionnaire);
  }

  update(questionnaire: QuestionnaireAnswer): Observable<QuestionnaireAnswer> {
    const url = `${this.apiUrl}`;
    return this.http.put<QuestionnaireAnswer>(url, questionnaire);
  }

  getByEvaluationId(periodId:number): Observable<QuestionnaireAnswer[]> {
    const url = `${this.apiUrl}evaluationId/${periodId}`;
    return this.http.get<QuestionnaireAnswer[]>(url);
  }

  saveAnswersList(answers:QuestionnaireAnswer[]): Observable<QuestionnaireAnswer[]>{
    const url = this.apiUrl+'updateList';
    return this.http.put<QuestionnaireAnswer[]>(url,answers);
  }

  interactedAnswer(questionnaireId:number,answers:QuestionnaireAnswer): Observable<QuestionnaireAnswer>{
    const url = this.apiUrl+`questionnaireAnswer/${questionnaireId}`;
    return this.http.post<QuestionnaireAnswer>(url,answers);
  }

}
