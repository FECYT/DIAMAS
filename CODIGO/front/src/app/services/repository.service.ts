import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Repository } from '../interfaces/repository.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  private apiUrl = environment.host + 'repositories'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  getRepositories(): Observable<Repository[]> {
    const url = `${this.apiUrl}/getAll`;
    return this.http.get<Repository[]>(url);
  }

  getRepositoryById(id: number): Observable<Repository> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Repository>(url);
  }

  getRepositoryByUserId(id: number): Observable<Repository> {
    const url = `${this.apiUrl}/repository/userId/${id}`;
    return this.http.get<Repository>(url);
  }

  createRepository(repository: Repository): Observable<Repository> {
    return this.http.post<Repository>(this.apiUrl, repository);
  }

  updateRepository(repository: Repository): Observable<Repository> {
    const url = `${this.apiUrl}/${repository.id}`;
    return this.http.put<Repository>(url, repository);
  }

  deleteRepository(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  getRepositoryByIdRepositoryDnet(dnetId: string) {
    const url = `${this.apiUrl}/repositoryIdDnet/${dnetId}`;
    return this.http.get<Repository>(url);
  }
}
