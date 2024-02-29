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

  getByPeriodId(periodId: number): Observable<FileInterface[]> {
    const url = `${this.apiUrl}periodId/${periodId}`;
    return this.http.get<FileInterface[]>(url);
  }

  saveAnswersList(answers: FileInterface[]): Observable<FileInterface[]> {
    const url = this.apiUrl + 'updateList';
    return this.http.put<FileInterface[]>(url, answers);
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

  /* Métodos para subir y descargar archivos. */
  /* onFileChange(event: any) {
  if (event.target.files.length > 0) {
    this.fileToUpload = event.target.files[0];
    Array.from(event.target.files).forEach((file: any) => {
      this.uploadFile(file);
  });

  }
}

uploadFile(file: File) {
  const formData = new FormData();

  // Supongamos que 'newDocumento' es un objeto que quieres enviar junto con el archivo.
  const miVariable = {
    id: 1,
    normalizedDocumentName: "documento_prueba.txt",
    fileFormat: "txt",
    fileSize: 1024,
    aswerDateTime: "2023-09-25T12:00:00",
    fileHash: "a1b2c3d4e5f6",
    filePath: null,
    nDeleteState: 1
  };

  let newDocumento = miVariable;

  formData.append(
    'file',
    new Blob([JSON.stringify(newDocumento)], {
      type: 'application/json',
    })
  );

  // Agregar el archivo al FormData
  formData.append("fichero", file);

  this.fileService.create(formData).subscribe(res =>{
    console.log(res);
  });
}

download(id: number){
  this.fileService.download(id).subscribe(blob => {
    const a = document.createElement('a');
    const objectUrl = URL.createObjectURL(blob);
    a.href = objectUrl;
    a.download = 'filename_you_want.pdf';  // Si tienes la información del nombre del archivo, puedes reemplazar 'filename_you_want.ext' con el nombre real.
    a.click();
    URL.revokeObjectURL(objectUrl);
});
} */
}
