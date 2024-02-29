import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';
import { MatTableDataSource } from '@angular/material/table';
import { Evaluacion } from '../components/lista-evaluaciones/lista-evaluaciones.component';
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
export class ConfigBBDDService {
  private apiUrl = environment.host;

  constructor(private http: HttpClient) {}
  getAutomaticRegister(): Observable<Boolean> {
    return this.http.get<Boolean>(`${this.apiUrl}config/getAutomaticRegister`);
  }

  setAutomaticRegister(status:Boolean): Observable<Boolean> {
    return this.http.get<Boolean>(`${this.apiUrl}config/setAutomaticRegister/${status}`);
  }

}
