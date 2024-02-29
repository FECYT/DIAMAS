import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssignedEvaluator } from '../interfaces/assigned-evaluator.interface';
import { User } from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AssignedEvaluatorService {
  private baseUrl = environment.host; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  create(asignedEvaluator: AssignedEvaluator): Observable<AssignedEvaluator> {
    return this.http.post<AssignedEvaluator>(`${this.baseUrl}assignedEvaluators`, asignedEvaluator);
  }

  update(asignedEvaluator: AssignedEvaluator): Observable<AssignedEvaluator> {
    return this.http.put<AssignedEvaluator>(`${this.baseUrl}assignedEvaluators/${asignedEvaluator.id}`, asignedEvaluator);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}assignedEvaluators/${id}`);
  }

  findAll(): Observable<AssignedEvaluator[]> {
    return this.http.get<AssignedEvaluator[]>(`${this.baseUrl}assignedEvaluators`);
  }

  findById(id: number): Observable<AssignedEvaluator> {
    return this.http.get<AssignedEvaluator>(`${this.baseUrl}assignedEvaluators/${id}`);
  }

  getAssignedEvaluatorsObservable(evaluationId: number): Observable<AssignedEvaluator[]> {
    return this.http.get<AssignedEvaluator[]>(`${this.baseUrl}assignedEvaluators?evaluationId=${evaluationId}`);
  }

  /**
   * Obtiene los usuarios evaluadores asignados a una evaluación específica.
   *
   * @param evaluationId - El ID de la evaluación.
   * @returns Observable con el array de usuarios evaluadores asignados.
   */
  findByEvaluationId(evaluationId: number): Observable<AssignedEvaluator[]> {
    return this.http.get<AssignedEvaluator[]>(`${this.baseUrl}assignedEvaluators/evaluation/${evaluationId}`);
  }

  assignEvaluatorsToEvaluation(evaluationId: number, evaluators: User[]): Observable<any> {
      return this.http.post<any>(`${this.baseUrl}assignedEvaluators/evaluation/${evaluationId}`, evaluators);
  }

  editAssignedEvaluators(evaluationId: number, evaluators: User[]): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}assignedEvaluators/evaluation/edit/${evaluationId}`, evaluators);
  }

  getActiveEvaluations() {
    const url = `${this.baseUrl}assignedEvaluators/getActiveEvaluations/`;
    return this.http.get<AssignedEvaluator[]>(url);
  }


}
