import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {User, UserPlusRoles} from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';
import {ChangePasswordBean} from "../interfaces/ChangePasswordBean";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = environment.host + 'api/';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}users`);
  }

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}users/email/${email}`);
  }

  doesEmailExist(email: string): Observable<Boolean> {
    return this.http.get<Boolean>(`${this.apiUrl}users/doesEmailExist/${email}`);
  }
  updateUserPlusRoles(user: UserPlusRoles): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}users/updateWithRoles`, user);
  }

  changeUserPassword(user: ChangePasswordBean): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}users/changePassword`, user);
  }


  toggleUserActivity(id: number, status: Boolean): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}users/toggle/${id}/${status}`,'');
  }


}
