import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { SuccessModalComponent } from 'src/app/components/success-modal/success-modal.component';
import {Router} from "@angular/router";
import {AuthService} from "../authservice.service";
import {TranslateService} from "@ngx-translate/core";

type RolEspanol = 'EVALUADOR' | 'SUPER EVALUADOR' | 'ADMINISTRADOR' | 'RESPONSABLE REPOSITORIO';


@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private translateService: TranslateService,private dialog: MatDialog, private sanitizer: DomSanitizer, private router:Router, private authService:AuthService) {}

/**
 * Método privado para manejar errores. Muestra un modal de error.
 * @param error - Error capturado.
 */
  handleError(error: any): void {
    this.dialog.open(SuccessModalComponent, {
      data: {
        title: 'Error',
        content: this.sanitizeHtml(error),
      },
      width: '50%',
      height: '60%'
    });
  }
/**
 * Método privado para manejar mensajes de exito. Muestra un modal.
 * @param success - Mensaje de Éxito.
 */
  handleSuccess(success: any): void {
    this.dialog.open(SuccessModalComponent, {
      data: {
        title: this.translateService.instant('EXITO'),
        content: this.sanitizeHtml(success),
      },
      width: '50%',
      height: '60%'
    });
  }
/**
 * Método privado para manejar mensajes de información. Muestra un modal.
 * @param info - Mensaje de información.
 */
  handleInfo(info: any): void {
    this.dialog.open(SuccessModalComponent, {
      data: {
        title: 'Información',
        content: this.sanitizeHtml(info),
      },
      width: '50%',
      height: '60%'
    });
  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }



  //TODO TERMINAR
  travelToFecyt(target:string,openInNewTab:Boolean){

    //Primero chekeo si el user está logueado para enviarlo directamente a la URL o hacer el paso intermedio
    const currentUser = this.authService.currentUser;

    if (currentUser == null) {
      openInNewTab ? window.open(target, '_blank') : window.location.href = target;
    } else {

      const email = currentUser.email
      //TODO AQUI va la URL intermedia y pasar por params email y targetURL
      openInNewTab ? window.open(target, '_blank') : window.location.href = target;

    }

  }


}
