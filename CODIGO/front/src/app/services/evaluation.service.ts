import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';
import { MatTableDataSource } from '@angular/material/table';
import { Evaluacion } from '../components/lista-evaluaciones/lista-evaluaciones.component';
import { SharedDataService } from './shared-data.service';
import { EvaluationActionHistoryService } from './evaluation-action-history.service';
import { Repository } from '../interfaces/repository.interface';
import { User } from '../interfaces/user.interface';
import { AuthService } from './authservice.service';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class EvaluationService {
  private apiUrl = environment.host; // Reemplaza con la URL de tu API
  dataSource: MatTableDataSource<Evaluacion> = new MatTableDataSource<Evaluacion>();
  dataSource2: MatTableDataSource<Evaluation> = new MatTableDataSource<Evaluation>();
  usuarioLogado: User | null;

  constructor(private http: HttpClient, private sharedService: SharedDataService,private evaluationActionHistoryService: EvaluationActionHistoryService, private authService: AuthService) {
    this.usuarioLogado = this.authService.currentUser;  // Suponiendo que el servicio AuthService tiene un método getLoggedUser que retorna los detalles del usuario logado.
  }
  create(evaluation: Evaluation): Observable<Evaluation> {
    return this.http.post<Evaluation>(`${this.apiUrl}evaluation`, evaluation);
  }

  update(evaluation: Evaluation): Observable<Evaluation> {
    return this.http.put<Evaluation>(`${this.apiUrl}evaluation/${evaluation.id}`, evaluation);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}evaluation/${id}`);
  }

  findAll(): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation`);
  }

  findById(id: number): Observable<Evaluation> {
    return this.http.get<Evaluation>(`${this.apiUrl}evaluation/${id}`);
  }

  // Método para obtener una evaluación según el tipo de cuestionario
  findByQuestionnaireType(id: number): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/questionnaireType/${id}`);
  }  

  findActiveEvaluationsOfUser(id: number): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/findActiveEvaluationsOfUser/${id}`);
  }

  findAllEvaluationsWithUsers(): Observable<Evaluation[]> {
    return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/findAllEvaluationsWithUsers/`);
  }

  deleteEvaluationAndQuestionnaire(evaluation:Evaluation): Observable<Boolean> {
    return this.http.post<Boolean>(`${this.apiUrl}evaluation/deleteEvaluationAndQuestionnaire`,evaluation);
  }

    /**
   * Encuentra todas las evaluaciones que pertenecen a un repositorio específico y que tienen el campo closeDate como null.
   *
   * @param repoId - El ID del repositorio para el cual buscar evaluaciones.
   * @returns Observable con un array de evaluaciones que cumplen con las condiciones.
   */
    findAllWithoutCloseDateForRepository(repoId: number): Observable<Evaluation[]> {
      return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/findAllWithoutCloseDateForRepository/${repoId}`);
    }

    /**
     * Encuentra todas las evaluaciones que pertenecen a un repositorio específico y que tienen el campo closeDate no como null.
     *
     * @param repoId - El ID del repositorio para el cual buscar evaluaciones.
     * @returns Observable con un array de evaluaciones que cumplen con las condiciones.
     */
    findAllWithCloseDateForRepository(repoId: number): Observable<Evaluation[]> {
      return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/findAllWithCloseDateForRepository/${repoId}`);
    }

    findAllWithCloseDateForRepositoryDnet(dnetRepoId: string): Observable<Evaluation[]> {
      return this.http.get<Evaluation[]>(`${this.apiUrl}evaluation/findAllWithCloseDateForRepositoryDNET/${dnetRepoId}`);
    }


/*  getSampleData(): Evaluacion[] {
    let data: Evaluacion[] = [];

    for (let i = 1; i <= 5; i++) {
      let item: Evaluacion = {
        nombre: `Repositorio de prueba ${i}`,
        ultimaModificacion: this.generateRandomDate(),
        evaluacion: 'En proceso',
        evaluadores: this.generateRandomName(),
        history: this.evaluationActionHistoryService.getSampleData()

      };
      data.push(item);
    }

    let responsableRepositorioVacio: Evaluacion = {
      nombre: `Repositorio de prueba 6`,
      ultimaModificacion: this.generateRandomDate(),
      evaluacion: 'Pendiente',
      evaluadores: null
    };
    data.push(responsableRepositorioVacio);

    this.dataSource = new MatTableDataSource(data);

    this.sharedService.repositorioObservable.subscribe(data => {
      if (data) {
          const repoIndex = this.dataSource.data.findIndex(repo => repo.nombre === data.nombre);
          if (repoIndex !== -1) {
              this.dataSource.data[repoIndex].evaluadores = data.evaluadores.join(", ");
          }
      }
    });
    return data;
  }
*/
  /*generateRandomDate(): string {
    const year = 2023;
    const month = 6; // Julio es el mes 6 en JavaScript (enero = 0)
    const day = Math.floor(Math.random() * 31) + 1;
    const hours = Math.floor(Math.random() * 24);
    const minutes = Math.floor(Math.random() * 60);
    const seconds = Math.floor(Math.random() * 60);

    return `${year}/${this.padZero(month + 1)}/${this.padZero(day)} ${this.padZero(hours)}:${this.padZero(minutes)}:${this.padZero(seconds)}`;
  }
*/
  padZero(number: number): string {
    return number < 10 ? `0${number}` : `${number}`;
  }

/*  generateRandomName(): string {
    const names = ["Juan", "María", "Luis", "Ana", "Carlos", "Elena"];
    const lastNames = ["García", "Martínez", "López", "Rodríguez", "Pérez", "Gómez"];
    const randomName = names[Math.floor(Math.random() * names.length)];
    const randomLastName = lastNames[Math.floor(Math.random() * lastNames.length)];
    return `${randomName} ${randomLastName}`;
  }
  getSampleDataObservable(): Observable<Evaluacion[]> {
    return of(this.getSampleData());
  }

  getSampleData2(): Evaluation[] {
    let data: Evaluation[] = [];

    for (let i = 1; i <= 5; i++) {
      let item: Evaluation = {
        id: i,
        repository: this.generateRandomRepository(i),
        lastEdited: this.generateRandomDate2(),
        evaluationState: 'En proceso',
        closeDate: null,
        evaluationGrade: null,
        nDeleteState: 0,
        evaluadores: this.generateRandomUsersFromSample()

      };
      data.push(item);
    }
    if (this.usuarioLogado && this.isSuperEvaluatorOrAdmin(this.usuarioLogado)) {
      let responsableRepositorioVacio: Evaluation = {
          id: 6,
          repository: this.generateRandomRepository(6),
          lastEdited: this.generateRandomDate2(),
          evaluationState: 'Pendiente',
          closeDate: null,
          evaluationGrade: null,
          nDeleteState: 0,
          evaluadores: null
      };

      data.push(responsableRepositorioVacio);
    }

    this.dataSource2 = new MatTableDataSource(data);

    return data;
  }
*/

/*  generateRandomDate2(): Date {
    const year = 2023;
    const month = 6; // July is the month 6 in JavaScript (January = 0)
    const day = Math.floor(Math.random() * 31) + 1;
    const hours = Math.floor(Math.random() * 24);
    const minutes = Math.floor(Math.random() * 60);
    const seconds = Math.floor(Math.random() * 60);

    return new Date(year, month, day, hours, minutes, seconds);
  }

  generateRandomRepository(i: number): Repository {
    return {
        id: this.generateRandomNumber(1, 1000), // Asigna un rango adecuado para el ID
        dnetId: this.generateRandomDnetId(),
        nombreDnet: this.generateRandomNombreDnet(i),
        nDeleteState: Math.floor(Math.random() * 1) // Suponiendo que quieres un número entre 0 y 4
    };
}

generateRandomNumber(min: number, max: number): number {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
*/
/*
generateRandomDnetId(): string {
    // Genera un ID aleatorio, por ejemplo, una combinación de letras y números
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';
    const length = 8; // Decide la longitud que deseas para el dnetId
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}

  generateRandomNombreDnet(i: number): string {
      // Si tienes un conjunto fijo de nombres posibles para nombreDnet, úsalos aquí.
      // De lo contrario, puedes generar uno aleatoriamente como el dnetId.
      //const names = ["Repositorio1", "Repositorio2", "Repositorio3", "Repositorio4", "Repositorio5", "Repositorio6"];
      //return names[Math.floor(Math.random() * names.length)];
      return "Repositorio "+i;
  }

  getSampleData2Observable(): Observable<Evaluation[]> {
    return of(this.getSampleData2());
  }

  generateRandomUser(): User {
    return {
        id: this.generateRandomNumber(1, 1000), // Asigna un rango adecuado para el ID
        lastLogin: this.generateRandomDate2(),
        dnetId: this.generateRandomDnetId(),
        userName: this.generateRandomUserName(),
        nDeleteState: Math.floor(Math.random() * 2), // Suponiendo que quieres un número entre 0 y 1
        nombre: this.generateRandomUserName(),
        apellido: this.generateRandomUserName(),
        email: 'prueba@prueba.com',
        rol: []
        };
  }

  generateRandomUsers(): User[] {
    const usersCount = Math.floor(Math.random() * 2); // Esto generará 0, 1 o 2
    const users: User[] = [];

    for (let i = 0; i < usersCount; i++) {
        users.push({
            id: this.generateRandomNumber(1, 1000), // Asigna un rango adecuado para el ID
            lastLogin: this.generateRandomDate2(),
            dnetId: this.generateRandomDnetId(),
            userName: this.generateRandomUserName(),
            nDeleteState: Math.floor(Math.random() * 2), // Suponiendo que quieres un número entre 0 y 1
            nombre: this.generateRandomUserName(),
            apellido: '',
            email: 'prueba@prueba.com',
            rol: []
        });
    }

    return users;
}

generateRandomUsersFromSample(): User[] {
  const usersCount = Math.floor(Math.random() * 2) + 1;
  const randomUsers: User[] = [];
  const tempUsers = [...this.authService.users];

  for (let i = 0; i < usersCount; i++) {
      const randomIndex = Math.floor(Math.random() * tempUsers.length);
      randomUsers.push(tempUsers[randomIndex]);
      //tempUsers.splice(randomIndex, 1);
  }

  return randomUsers;
}


  generateRandomUserName(): string {
    // Suponiendo que quieres combinar un nombre y un apellido para generar el userName.
    const names = ["Juan", "María", "Luis", "Ana", "Carlos", "Elena"];
    const lastNames = ["García", "Martínez", "López", "Rodríguez", "Pérez", "Gómez"];
    const randomName = names[Math.floor(Math.random() * names.length)];
    const randomLastName = lastNames[Math.floor(Math.random() * lastNames.length)];
    return `${randomName} ${randomLastName}`;  // Por ejemplo: "Juan Garcia"
  }
*/

}
