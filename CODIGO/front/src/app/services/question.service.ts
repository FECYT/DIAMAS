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

  /**
   * Verifica si una categoría tiene preguntas asociadas.
   * @param id - El ID de la categoría.
   * @returns Observable<boolean> - Un observable que emite un booleano indicando si la categoría tiene preguntas asociadas.
   */
  hasQuestionsByid(id: number): Observable<boolean> {
    // Asume que el endpoint de tu API para verificar es: /question/hasQuestionsByCategory/{id}
    const url = `${this.apiUrl}/hasQuestionsByCategory/${id}`;
    return this.http.get<boolean>(url);
  }

  getQuestionsByPeriodId(id:number): Observable<Question[]>{
    const url = `${this.apiUrl}/evaluationPeriod/${id}`;
    return this.http.get<Question[]>(url);
  }

  getQuestionsWithFileByYear(idType: number, year: number): Observable<Question[]> {
    const url = `${this.apiUrl}/hasFilePeriodByYear/${idType}/${year}`;
    return this.http.get<Question[]>(url);
  }

  insertQuestionSet(questions:Question[]): Observable<Question[]>{
    const url = `${this.apiUrl}/insertQuestionSet`;
    return this.http.post<Question[]>(url, questions);
  }

  getQuestionsByQuestionnaires(questionnaires: Questionnaire[]): Observable<Question[]>{
    const url = `${this.apiUrl}/questionnaires`;
    return this.http.post<Question[]>(url, questionnaires);
  }

}
