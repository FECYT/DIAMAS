import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { SHA256 } from 'crypto-js';

@Injectable({
  providedIn: 'root',
})
export class JwtDecoderService {
  decodeToken(token: string): any {
    if (token) {
      try {
        const decodedToken = jwt_decode(token);

        return decodedToken;
      } catch (error) {

      }
    } else {

    }

    return null;
  }

  public getHashFromObject(obj: any): string {
    // Convertir el objeto a una cadena JSON y luego calcular el hash
    const jsonString = JSON.stringify(obj);
    const hash = this.calculateHash(jsonString);

    return hash;
  }

  public calculateHash(data: string): string {
    const hash = SHA256(data).toString();

    return hash;
  }
}
