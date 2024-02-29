import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CatQuestion } from '../interfaces/cat-question.interface';  // Asegúrate de importar desde la ruta correcta
import { environment } from 'src/environments/environment';
import { Question } from '../interfaces/question.interface';

@Injectable({
  providedIn: 'root'
})
export class CatQuestionService {
  private apiUrl = environment.host + 'catQuestions'; // Reemplaza con la URL de tu API para CatQuestion

  constructor(private http: HttpClient) { }

  create(catQuestion: CatQuestion): Observable<CatQuestion> {
    return this.http.post<CatQuestion>(this.apiUrl, catQuestion);
  }

  update(id: number, catQuestion: CatQuestion): Observable<CatQuestion> {
    const url = `${this.apiUrl}`;
    return this.http.put<CatQuestion>(url, catQuestion);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  findAll(): Observable<CatQuestion[]> {
    return this.http.get<CatQuestion[]>(this.apiUrl);
  }

  findById(id: number): Observable<CatQuestion> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<CatQuestion>(url);
  }

  // Método para obtener una categoría según el tipo de cuestionario
  findByQuestionnaireType(id: number): Observable<CatQuestion[]> {
    return this.http.get<CatQuestion[]>(`${this.apiUrl}/questionnaireType/${id}`);
  }  

}


