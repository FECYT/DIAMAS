import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './authservice.service';
import { Usuario } from '../components/login/models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const roles = route.data['roles'] as Array<string>;


    const currentUser = this.authService.currentUser;


    if (currentUser && currentUser.rol!.some(r => roles.includes(r))) {
      return true;
    }

    this.router.navigate(['/acceso-denegado']);
    return false;
  }

}
