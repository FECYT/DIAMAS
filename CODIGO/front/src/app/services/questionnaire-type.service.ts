import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { QuestionnaireType } from '../interfaces/questionnaire-type.interface';

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireTypeService {
  private apiUrl = environment.host + 'api/questionnaireType/'; // Ajusta la URL de tu API para Questionnaire

  constructor(private http: HttpClient) { }

  getQuestionnairesType(): Observable<QuestionnaireType[]> {
    return this.http.get<QuestionnaireType[]>(this.apiUrl);
  }

}
