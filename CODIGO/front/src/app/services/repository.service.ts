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

  getRepositoryByUserId(id: number): Observable<Repository> {
    const url = `${this.apiUrl}/repository/userId/${id}`;
    return this.http.get<Repository>(url);
  }

}
