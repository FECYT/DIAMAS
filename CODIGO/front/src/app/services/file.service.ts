import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Questionnaire } from '../interfaces/questionnaire.interface';
import { Observable } from 'rxjs';
import { QuestionnaireAnswer } from '../interfaces/questionnaire-answer';
import { FileInterface } from '../interfaces/file.interface';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private apiUrl = environment.host + 'api/file/';

  constructor(private http: HttpClient) {}

  create(file: any, questionnaireId:number, questionId:number): Observable<FileInterface> {
    return this.http.post<FileInterface>(this.apiUrl + `create/${questionnaireId}/${questionId}`, file);
  }

  update(file: FileInterface): Observable<FileInterface> {
    const url = `${this.apiUrl}`;
    return this.http.put<FileInterface>(url, file);
  }

  download(fileId: number) {
    const url = `${this.apiUrl}download/${fileId}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  downloadZipByIds(selectedQuestions: any[]) {
    const url = `${this.apiUrl}downloadZip`;
    return this.http.post(url, selectedQuestions, {
      responseType: 'blob'
    });
  }

}
