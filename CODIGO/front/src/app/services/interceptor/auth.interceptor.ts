import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpHandler,
    HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../authservice.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // const token = this.authService.getToken();
        const token = sessionStorage.getItem('token');

        if (token) {
            // Clonamos la solicitud y agregamos el encabezado de autorización
            const authRequest = request.clone({
                setHeaders: {
                    Authorization: `${token}`
                }
            });

            // Continuamos con la solicitud clonada
            return next.handle(authRequest);
        } else {
            // Si no hay token, simplemente continuamos con la solicitud original
            return next.handle(request);
        }
    }
}
