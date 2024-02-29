import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EvaluationActionHistory } from '../interfaces/evaluation-action-history.interface';
import { EvaluacionActionHistory } from '../components/lista-evaluaciones/lista-evaluaciones.component';
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
  
  /**
   * Obtiene el historial de acciones de una evaluación concreta.
   *
   * @param evaluationId - El ID de la evaluación.
   * @returns Observable con el array del historial de acciones.
   */
  findActionHistoryByIdEvaluation(evaluationId: number): Observable<EvaluationActionHistory[]> {
    return this.http.get<EvaluationActionHistory[]>(`${this.baseUrl}evaluation-action-histories/evaluation/${evaluationId}`);
  }

  getSampleData(): EvaluacionActionHistory[] {
    // Genera y retorna datos de prueba
    let sampleData: EvaluacionActionHistory[] = [
      { fecha: this.generateRandomDate(), responsable: 'Admin', accion: 'Creación' },
      { fecha: this.generateRandomDate(), responsable: 'Usuario 2', accion: 'Eliminación' },
      { fecha: this.generateRandomDate(), responsable: 'Admin', accion: 'Restauración' },
      { fecha: this.generateRandomDate(), responsable: 'Admin', accion: 'Restauración' },
      { fecha: this.generateRandomDate(), responsable: 'Admin', accion: 'Restauración' },
      { fecha: this.generateRandomDate(), responsable: 'Admin', accion: 'Restauración' },
    ];
    return sampleData;
  }

  generateRandomDate(): string {
    const year = 2023;
    const month = 6; // Julio es el mes 6 en JavaScript (enero = 0)
    const day = Math.floor(Math.random() * 31) + 1;
    const hours = Math.floor(Math.random() * 24);
    const minutes = Math.floor(Math.random() * 60);
    const seconds = Math.floor(Math.random() * 60);

    return `${year}/${this.padZero(month + 1)}/${this.padZero(day)} ${this.padZero(hours)}:${this.padZero(minutes)}:${this.padZero(seconds)}`;
  }

  padZero(number: number): string {
    return number < 10 ? `0${number}` : `${number}`;
  }
}
