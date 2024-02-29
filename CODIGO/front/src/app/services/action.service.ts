import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Action } from '../interfaces/action.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActionService {
  private baseUrl = environment.host; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  create(action: Action): Observable<Action> {
    return this.http.post<Action>(`${this.baseUrl}actions`, action);
  }

  update(action: Action): Observable<Action> {
    return this.http.put<Action>(`${this.baseUrl}actions/${action.id}`, action);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}actions/${id}`);
  }

  findAll(): Observable<Action[]> {
    return this.http.get<Action[]>(`${this.baseUrl}actions`);
  }

  findById(id: number): Observable<Action> {
    return this.http.get<Action>(`${this.baseUrl}actions/${id}`);
  }
}
