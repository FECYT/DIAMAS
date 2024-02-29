import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-acceso-denegado',
  templateUrl: './acceso-denegado.component.html',
  styleUrls: ['./acceso-denegado.component.scss']
})
export class AccesoDenegadoComponent {
  constructor(private router: Router) { }

  goHome() {
    this.router.navigate(['/']);
  }

  goLogin() {
    this.router.navigate(['/login']);
  }
}
