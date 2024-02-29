import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Action } from '../interfaces/action.interface';
import { environment } from 'src/environments/environment';
import {User} from "../interfaces/user.interface";

@Injectable({
  providedIn: 'root'
})
export class DnetService {
  private baseUrl = environment.host; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }


  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  getRepositoriesByEmail(email: string): Observable<any[]> {

    return this.http.get<any[]>(`${this.baseUrl}dnet/repositoryByEmail`, {params: {email}});
  }

  getEvaluators(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}dnet/evaluators`);
  }

}
