import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EvaluationActionHistory } from '../interfaces/evaluation-action-history.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EvaluationActionHistoryService {

  private baseUrl = environment.host; // Reemplaza esto con la URL de tu API

  constructor(private http: HttpClient) { }

  create(evaluationActionHistory: EvaluationActionHistory): Observable<EvaluationActionHistory> {
    return this.http.post<EvaluationActionHistory>(`${this.baseUrl}evaluation-action-histories`, evaluationActionHistory);
  }

  update(evaluationActionHistory: EvaluationActionHistory): Observable<EvaluationActionHistory> {
    return this.http.put<EvaluationActionHistory>(`${this.baseUrl}evaluation-action-histories/${evaluationActionHistory.id}`, evaluationActionHistory);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}evaluation-action-histories/${id}`);
  }

  findAll(): Observable<EvaluationActionHistory[]> {
    return this.http.get<EvaluationActionHistory[]>(`${this.baseUrl}evaluation-action-histories`);
  }

  findById(id: number): Observable<EvaluationActionHistory> {
    return this.http.get<EvaluationActionHistory>(`${this.baseUrl}evaluation-action-histories/${id}`);
  }

}
