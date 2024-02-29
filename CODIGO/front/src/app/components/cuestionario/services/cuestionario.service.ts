import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pregunta } from '../models/pregunta.model';

@Injectable({
    providedIn: 'root'
})
export class CuestionarioService {
/*
    private apiUrl = 'tu_api_endpoint';

    constructor(private http: HttpClient) {}

    obtenerPreguntas(): Observable<Pregunta[]> {
        return this.http.get<Pregunta[]>(`${this.apiUrl}/preguntas`);
    }

    enviarRespuestas(respuestas: Pregunta[]): Observable<any> {
        return this.http.post(`${this.apiUrl}/guardarRespuestas`, respuestas);
    }
    */
}
