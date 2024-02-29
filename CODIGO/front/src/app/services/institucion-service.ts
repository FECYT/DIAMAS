import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {QuestionnaireAnswer} from "../interfaces/questionnaire-answer";
import {Stats, StatsDivided} from "../interfaces/Stats";
import {Institucion} from "../interfaces/Institucion";

@Injectable({
  providedIn: 'root'
})
export class InstitucionService {

  private apiUrl = environment.host + 'instituciones'; // Ajusta la URL de tu API para Questionnaire

  constructor(private http: HttpClient) { }

  findByUserId(id:number): Observable<Institucion> {
    return this.http.get<Institucion>(this.apiUrl+`/findByUserId/${id}`);
  }




}
