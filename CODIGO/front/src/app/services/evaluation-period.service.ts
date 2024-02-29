import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { EvaluationPeriod } from '../interfaces/evaluation-period.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EvaluationPeriodService {
  private apiUrl = environment.host; // Reemplaza esto con la URL real de tu API

  constructor(private http: HttpClient) { }

  // Método para crear un nuevo período de evaluación
  create(evaluationPeriod: EvaluationPeriod): Observable<EvaluationPeriod> {
    return this.http.post<EvaluationPeriod>(`${this.apiUrl}evaluationPeriod`, evaluationPeriod);
  }

  // Método para actualizar un período de evaluación existente
  update(evaluationPeriod: EvaluationPeriod): Observable<EvaluationPeriod> {
    return this.http.put<EvaluationPeriod>(`${this.apiUrl}evaluationPeriod/${evaluationPeriod.id}`, evaluationPeriod);
  }

  // Método para eliminar un período de evaluación
  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`${this.apiUrl}evaluationPeriod/${id}`);
  }

  // Método para obtener todos los períodos de evaluación
  findAll(): Observable<EvaluationPeriod[]> {
    return this.http.get<EvaluationPeriod[]>(`${this.apiUrl}evaluationPeriod`);
  }

  // Método para obtener un período de evaluación por su ID
  findById(id: number): Observable<EvaluationPeriod> {
    return this.http.get<EvaluationPeriod>(`${this.apiUrl}evaluationPeriod/${id}`);
  }

  // Método para obtener un período de evaluación por el tipo de cuestionario
  findByQuestionnaireType(id: number): Observable<EvaluationPeriod[]> {
    return this.http.get<EvaluationPeriod[]>(`${this.apiUrl}evaluationPeriod/questionnaireType/${id}`);
  }

  // Método para obtener el último período de evaluación con estado "Abierto"
  findLatestOpen(idType: number): Observable<EvaluationPeriod | null> {
    return this.findAll().pipe(
      map(periods => periods.filter(p => p.status === 'Abierto' && p.questionnaireType.id === idType)),
      map(openPeriods => {
        if (openPeriods.length > 0) {
          return openPeriods.reduce((latest, current) =>
            new Date(latest.startDate).getTime() > new Date(current.startDate).getTime() ? latest : current
          );
        }
        return null;
      })
    );
  }

}
