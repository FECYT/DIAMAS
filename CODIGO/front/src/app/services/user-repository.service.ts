import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserRepositoryService {
  private apiUrl = environment.host;

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}user-repository`);
  }

  getUserByRepository(idRepository: number) {
    return this.http.get<any>(`${this.apiUrl}user-repository/repository/${idRepository}`);
  }

  doesUserOwnRepository(idUser:number,idRepository:number): Observable<Boolean> {
    return this.http.get<Boolean>(`${this.apiUrl}user-repository/doesUserOwnRepository/${idUser}/${idRepository}`);
  }

  getWithPeriod(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}user-repository/withPeriod`);
  }

  getWithInstitution(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}user-repository/withInstitution`);
  }

}
