import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExportService {
  private apiUrl = environment.host; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  exportarRespuesta(fechaInicio: Date, fechaFinal: Date, formato: string): void {
    const data = {
      fechaInicio: fechaInicio,
      fechaFinal: fechaFinal,
      formato: formato
    };

    this.http.post(this.apiUrl, data, { responseType: 'blob' }).subscribe((response: Blob) => {
      const fileName = `respuesta_${new Date().toISOString()}.${formato}`;
      saveAs(response, fileName);
    });
  }

  exportarDocumento(fecha: Date, preguntas: any[]): void {
    const data = {
      fecha: fecha,
      preguntas: preguntas
    };

    this.http.post(this.apiUrl, data, { responseType: 'blob' }).subscribe((response: Blob) => {
      const fileName = `documento_${new Date().toISOString()}.zip`;
      saveAs(response, fileName);
    });
  }
}
