import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AssignedRepository } from '../interfaces/assigned-repository.interface';


@Injectable({
  providedIn: 'root'
})
export class AssignedRepositoryService {
  private baseUrl = 'URL_DE_TU_API'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  create(assignedRepository: AssignedRepository): Observable<AssignedRepository> {
    return this.http.post<AssignedRepository>(`${this.baseUrl}/assigned-repository`, assignedRepository);
  }

  update(assignedRepository: AssignedRepository): Observable<AssignedRepository> {
    return this.http.put<AssignedRepository>(`${this.baseUrl}/assigned-repository/${assignedRepository.id}`, assignedRepository);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/assigned-repository/${id}`);
  }

  findAll(): Observable<AssignedRepository[]> {
    return this.http.get<AssignedRepository[]>(`${this.baseUrl}/assigned-repository`);
  }

  findById(id: number): Observable<AssignedRepository> {
    return this.http.get<AssignedRepository>(`${this.baseUrl}/assigned-repository/${id}`);
  }
}
