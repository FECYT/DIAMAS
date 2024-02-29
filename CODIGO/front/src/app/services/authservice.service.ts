import { Injectable } from '@angular/core';
import { Usuario } from '../components/login/models/usuario.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { JwtDecoderService } from './jwt-decoder.service';
import { Router } from '@angular/router';
import {FileInterface} from "../interfaces/file.interface";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private _currentUser = new BehaviorSubject<User | null>(this.getUserFromSessionStorage());
    currentUser$ = this._currentUser.asObservable();
    private apiUrl = environment.host;
    private token: string | null = null;



    constructor(private http: HttpClient, private jwtDecoderService: JwtDecoderService,  private router: Router) { }



    setToken(token: string): void {
        this.token = token;
        sessionStorage.setItem('token', token);
    }

    login(email: string, password: string): Observable<any> {

        const encodedEmail = btoa(email);
        const encodedPassword = btoa(password);
        const credentials = {
            email: encodedEmail,
            password: encodedPassword,
        };

        try {
            return this.http.post<any>(this.apiUrl + 'user/iniciarSesion', credentials, { observe: 'response' })
                .pipe(
                    map((response: HttpResponse<any>) => {

                        const token = response.headers.get('Authorization');
                        if (token) {
                            this.setToken(token);
                            const decodedToken = this.jwtDecoderService.decodeToken(token);
                            const decodedTokenObject = JSON.stringify(decodedToken);

                            sessionStorage.setItem('decodedToken', decodedTokenObject);
                            const headerHash = this.jwtDecoderService.getHashFromObject(
                                decodedToken?.header
                            );
                        }
                    })
                );
        } catch (error) {
            throw new Error('No se pudo extraer el token');
        }
    }

    registerUser(user: User): Observable<any> {

      const encodedEmail = btoa(user.email!!);
      const encodedPassword = btoa(user.password!!);
      user.email = encodedEmail
      user.password = encodedPassword

      return this.http.post<number>(this.apiUrl + 'user/registrar', user)

    }


    users: User[] = []

    getLoginUser(email: string, password: string): User | null {
        // Ejemplo mock de login
        const user = this.users.find(u => u.email === email);

        if (user) {

            // Simulamos la obtención de un token desde el servidor
            const fakeToken = btoa(email + password); // codifica en base64 solo para simular
            sessionStorage.setItem('userToken', fakeToken);
            sessionStorage.setItem('currentUser', JSON.stringify(user));

            return user;
        }
        this.logout();
        return null;
    }



    logout() {
        this._currentUser.next(null);
        sessionStorage.removeItem('userToken');
        sessionStorage.removeItem('currentUser');
        this.router.navigate(['/']);
    }

    private getUserFromSessionStorage(): User | null {
        const storedUser = sessionStorage.getItem('currentUser');
        return storedUser ? JSON.parse(storedUser) : null;
    }

    // Agrega esta línea para definir la propiedad currentUser
    public usuario: Usuario | null = null;


    get currentUser(): User | null {
        return this._currentUser.value;
    }


    setCurrentUser(user: User){
        this._currentUser.next(user);
        sessionStorage.setItem('currentUser', JSON.stringify(user));
    }


}
