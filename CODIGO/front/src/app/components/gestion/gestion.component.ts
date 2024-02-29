import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestion',
  templateUrl: './gestion.component.html',
  styleUrls: ['./gestion.component.scss']
})
export class GestionComponent {
  constructor(private router: Router) { }

  administrarPeriodos(): void {
    this.router.navigate(['/administrar-periodos']);
  }
  administrarCategorias(): void {
    this.router.navigate(['/lista-terminos']);
  }
  exportacionRespuestas(): void {
    this.router.navigate(['/exportar-respuestas']);
  }
  exportacionDocumentos(): void {
    this.router.navigate(['/exportar-documentos']);
  }
  descargarCertificados(): void {
    this.router.navigate(['/descargar-certificados']);
  }
  listaActiva(): void {
    this.router.navigate(['/listaEvaluacionesActiva']);
  }
  userPanel(){
    this.router.navigate(['/user-gestion']);
  }
  statisticsTool(){
    this.router.navigate(['/estadisticas']);
  }
}
