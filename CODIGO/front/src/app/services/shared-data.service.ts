import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Evaluation } from '../interfaces/evaluation.interface';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  private informeAccedidoSource = new BehaviorSubject<boolean>(false);
  informeAccedido$ = this.informeAccedidoSource.asObservable();

  setInformeAccedido(status: boolean) {
    this.informeAccedidoSource.next(status);
  }

  private repositorioSubject = new BehaviorSubject<{nombre: string, evaluadores: string[], idEvaluacion: number} | null>(null);
    repositorioObservable = this.repositorioSubject.asObservable();

  updateRepositorio(data: {nombre: string, evaluadores: string[], idEvaluacion: number}) {
      this.repositorioSubject.next(data);
  }
}
