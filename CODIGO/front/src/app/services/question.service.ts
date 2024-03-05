import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Question, QuestionDTO} from '../interfaces/question.interface';
import { environment } from 'src/environments/environment';
import {Questionnaire} from "../interfaces/questionnaire.interface";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = environment.host + 'question'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  create(question: QuestionDTO): Observable<QuestionDTO> {
    return this.http.post<QuestionDTO>(this.apiUrl, question);
  }

  update(question: QuestionDTO): Observable<Question> {
    const url = `${this.apiUrl}`;
    return this.http.put<Question>(url, question);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  findAll(): Observable<Question[]> {
    return this.http.get<Question[]>(this.apiUrl);
  }

  findById(id: number): Observable<Question> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Question>(url);
  }


  getQuestionsByPeriodId(id:number): Observable<Question[]>{
    const url = `${this.apiUrl}/evaluationPeriod/${id}`;
    return this.http.get<Question[]>(url);
  }

  getQuestionsWithFileByYear(idType: number, year: number): Observable<Question[]> {
    const url = `${this.apiUrl}/hasFilePeriodByYear/${idType}/${year}`;
    return this.http.get<Question[]>(url);
  }


}
