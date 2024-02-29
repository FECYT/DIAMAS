import {Component, TRANSLATIONS} from '@angular/core';
import { Usuario } from './models/usuario.model';
import { AuthService } from '../../services/authservice.service';
import { Router } from '@angular/router'; // Importa el servicio Router
import { JwtHelperService } from '@auth0/angular-jwt';
import { UtilService } from 'src/app/services/general/util.service';
import { User } from 'src/app/interfaces/user.interface';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';

import {QuestionModalComponent} from "../question-modal/question-modal.component";
import {DomSanitizer} from "@angular/platform-browser";
import { MatDialog } from '@angular/material/dialog';
import {PasswordRecoverService} from "../../services/recoverPass-service";
import {TranslateService} from "@ngx-translate/core";
import {exportTranslations} from "../../global-constants";
import {CacheService} from "../../services/cache.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  usuario: User | null = null;
  email: string = '';
  password: string = '';
  showSpinner = false
  captchaCompleted: boolean = false;

  registerToggle = false
  registerPasswordRepeat = ""
  registerPassword = ""
  registerEmail = ""
  registerNombre = ""
  registerApellidos = ""
  registerInstitucion = ""
  registerAcronimo = ""
  registerURL = ""
  registerAfiliacion = ""
  registerCountry = ""

  registerPasswordsNotEqual = false
  registerEmailInvalid = false

  recoveringPass =false
  recoveringEmail = ""

  termsAceptados = false

  constructor(private cacheService:CacheService,private translateService:TranslateService,private passwordRecoverService: PasswordRecoverService,private sanitizer: DomSanitizer,private authService: AuthService,public dialog: MatDialog, private messageService: MessageService, private router: Router, public jwtHelper: JwtHelperService, public utilService: UtilService) {}

  onSubmit() {

     if (!this.captchaCompleted) {
      alert('Por favor complete el captcha');
      return; // Detener la ejecución si el captcha no está completado
    }

    this.showSpinner = true

    const encodedMail = this.email;
    const encodedPass = this.password;

    this.authService.login(encodedMail, encodedPass)
      .subscribe((userResult) => {

          this.usuario = this.authService.getLoginUser(this.email, this.password);
          const helper = new JwtHelperService();
          let token = sessionStorage.getItem('token') ? sessionStorage.getItem('token') : "";
          if (token) {
            const decodedToken = helper.decodeToken(token);

            const responseCode = decodedToken.returnCode

            switch (responseCode){

              case 0:
                //VA BIEN
                break;

              case 1:
                //NO EXISTE USUARIO
                this.errorNoExiste()
                return;

              case 2:
                //CONTRASEÑA INCORRECTA
                this.errorCredenciales()
                return;

              case 3:
                //EL USUARIO EXISTE PERO NO ESTÁ ACTIVADO. CONTACTA UN ADMINISTRADOR
                this.errorNoActivado()
                return;

            }

            const email = decodedToken.email;
            const recommendationSendEmail = decodedToken.recommendationSendEmail;
            const roles = decodedToken.roles;
            const numRepositorios = decodedToken.numRepositorios;
            const idUser = decodedToken.idUser;


            this.usuario = {
              email: email,
              recommendationSendEmail: Boolean(recommendationSendEmail),
              rol: roles,
              id: idUser
            }

            this.authService.setCurrentUser(this.usuario);
          } else {
            this.utilService.handleError("Error al obtener el token");
          }

        if (this.usuario && this.usuario.rol) {
          /*
          // Redirige según el rol del usuario
          if (this.usuario.rol!.includes('ADMINISTRADOR')) {
            this.router.navigate(['gestion']);
            window.scrollTo({ top: 0, behavior: 'smooth' });
          } else {
            // Redirige a una página por defecto si el rol no coincide con ninguno de los casos
            this.router.navigate(['/home-editor']);
            window.scrollTo({ top: 0, behavior: 'smooth' });
          }

           */
          sessionStorage.removeItem('module');

          this.router.navigate(['module-selector']);
          window.scrollTo({ top: 0, behavior: 'smooth' });
        }

      }, (error) => {

        this.errorConexion();
        this.showSpinner = false;
      }

      );

  }

  handleCaptcha(response: string) {
    if (response) {
      this.captchaCompleted = true;
      // Aquí puedes manejar la respuesta del captcha como necesites...
    } else {
      this.captchaCompleted = false;
    }
  }

  onRegisterClick(){
    this.registerToggle = !this.registerToggle
  }

  errorConexion() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR_CONEXION"), detail:this.getExportTranslation("NO_ESTABLECER_CONEXION")}]);
  }

  errorCredenciales() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("CREDENCIALES_INVALIDAS")}]);
  }

  errorNoActivado() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("USER_NO_ACTIVADO")}]);
  }

  errorNoExiste() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("NO_EXISTE_USER")}]);
  }

  successAvisaAdmin() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("EXITO"), detail:this.getExportTranslation("REGISTRADO_CON_EXITO")}]);
  }

  successEnviadoCorreo() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("EXITO"), detail:this.getExportTranslation("CORREO_ENVIADO")}]);
  }

  errorYaExiste() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("YA_EXISTE_EMAIL")}]);
  }

  showRecoverWidget(){
    this.recoveringPass = !this.recoveringPass
  }

  sendRecuperacion(){
    this.passwordRecoverService.generateNewCode(this.recoveringEmail).subscribe((data)=>{
      this.recoveringEmail = ""
      this.recoveringPass = !this.recoveringPass
      this.successEnviadoCorreo()
    })

  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  onCountrySelected(event:any){
    if (event == null) return;
    this.registerCountry = event.alpha2Code
  }

  getExportTranslation(key:string): string {
      const currentLanguage = this.cacheService.getLanguage()
      // @ts-ignore
      const translations = exportTranslations[currentLanguage];
      return translations[key] || '';
  }

  onChangeCheckbox() {
    this.termsAceptados = !this.termsAceptados
  }

  onRegisterSubmit() {
    if (this.registerCountry == "" || this.registerCountry == undefined) {
      alert('Por favor elija un país');
      return;
    }

    if (!this.termsAceptados){
      alert('Read and accept terms and conditions')
      return;
    }

    const emailPattern = /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/;

    if (!this.captchaCompleted) {
      alert('Por favor complete el captcha');
      return;
    }

    if (!emailPattern.test(this.registerEmail)) {
      this.registerEmailInvalid = true
      return;
    } else this.registerEmailInvalid = false

    if (this.registerPassword != this.registerPasswordRepeat){
      this.registerPasswordsNotEqual = true
      return;
    } else this.registerPasswordsNotEqual = false

    const user: User = {
      nombre: this.registerNombre,
      apellidos: this.registerApellidos,
      ipsp: this.registerInstitucion.toUpperCase(),
      email: this.registerEmail,
      password: this.registerPassword,
      acronimo: this.registerAcronimo.toUpperCase(),
      url: this.registerURL,
      afiliacion_institucional: this.registerAfiliacion.toUpperCase(),
      pais: this.registerCountry
    }

    this.authService.registerUser(user).subscribe((code)=>{

      switch (code){

        case 0:
          //Correcto, LOGIN
          this.email = this.registerEmail
          this.password = this.registerPassword
          this.onSubmit()
          break;

        case 1:
          //Correcto, contacta para activar
          this.successAvisaAdmin()
          this.router.navigate(['/']);
          break;

        case 2:
          //Ya existe usuario con este mail
          this.errorYaExiste()
          break;

      }

    })

  }

}
