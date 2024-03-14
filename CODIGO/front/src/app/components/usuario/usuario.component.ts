import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/authservice.service';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit, OnDestroy {
  usuario: User | null = null;
  private userSubscription!: Subscription;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.userSubscription = this.authService.currentUser$.subscribe(user => {
      this.usuario = user;
    });
  }

  ngOnDestroy(): void {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  cerrarSesion() {
    this.authService.logout();
    sessionStorage.removeItem('userToken');
    sessionStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
    return false;
  }

  getRolDisplay(): string {
    if (!this.usuario || !this.usuario.rol) {
      return 'DEFAULT';
    }
    // Si rol es un arreglo, verifica si 'EDITOR' está dentro del arreglo
    if (Array.isArray(this.usuario.rol) && this.usuario.rol.includes('EDITOR')) {
      return 'USER';
    }
    // Si rol no es un arreglo, procede con la comparación directa (o maneja el caso de arreglo de manera diferente)
    return Array.isArray(this.usuario.rol) ? this.usuario.rol.join(', ') : this.usuario.rol;
  }
}
