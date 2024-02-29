import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { environment } from 'src/environments/environment';
import {map, Observable} from "rxjs";
import {Repository} from "../interfaces/repository.interface";
import {PasswordRecover} from "../interfaces/PasswordRecover";

@Injectable({
  providedIn: 'root'
})
export class PasswordRecoverService {
  private apiUrl = environment.host; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  generateNewCode(email:string): Observable<void> {

    const emailHashed = btoa(email)

    const url = `${this.apiUrl}passwordRecover/email/${emailHashed}`;
    return this.http.post<void>(url,'');
  }

  isCodeCorrect(code:PasswordRecover):Observable<Boolean>{
    const url = `${this.apiUrl}passwordRecover`;
    return this.http.put<Boolean>(url,code);
  }

}
