import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';
import { MatTableDataSource } from '@angular/material/table';
import { SharedDataService } from './shared-data.service';
import { EvaluationActionHistoryService } from './evaluation-action-history.service';
import { Repository } from '../interfaces/repository.interface';
import { User } from '../interfaces/user.interface';
import { AuthService } from './authservice.service';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class EvaluationService {
  private apiUrl = environment.host; // Reemplaza con la URL de tu API
  dataSource2: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  usuarioLogado: User | null;

  constructor(private http: HttpClient, private sharedService: SharedDataService,private evaluationActionHistoryService: EvaluationActionHistoryService, private authService: AuthService) {
    this.usuarioLogado = this.authService.currentUser;  // Suponiendo que el servicio AuthService tiene un método getLoggedUser que retorna los detalles del usuario logado.
  }
  create(evaluation: Evaluation): Observable<Evaluation> {
    return this.http.post<Evaluation>(`${this.apiUrl}evaluation`, evaluation);
  }

  deleteEvaluationAndQuestionnaire(evaluation:Evaluation): Observable<Boolean> {
    return this.http.post<Boolean>(`${this.apiUrl}evaluation/deleteEvaluationAndQuestionnaire`,evaluation);
  }

  findByQuestionnaireType(id: number): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/questionnaireType/${id}`);
  }
}
