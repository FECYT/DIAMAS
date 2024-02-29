import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private configUrl = 'assets/config.json'; // Ruta al archivo JSON de configuración

  constructor(private http: HttpClient) {}

  getConfig(): Observable<any> {
    return this.http.get(this.configUrl);
  }

  getLimiteSubidaArchivos(): Observable<number> {
    return this.getConfig().pipe(
      map((config: any) => config.limiteMBSubidaArchivos)
    );
  }

  getLoginRoute(): Observable<string> {
    return this.getConfig().pipe(
      map((config: any) => config.loginRoute)
    );
  }

  getLoginExternal(): Observable<boolean> {
    return this.getConfig().pipe(
      map((config: any) => config.loginExternal)
    );
  }

  getIp(): Observable<number> {
    return this.getConfig().pipe(
      map((config: any) => config.ip)
    );
  }

}
