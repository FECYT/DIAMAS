import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PasswordRecoverService} from "../../services/recoverPass-service";
import {PasswordRecover} from "../../interfaces/PasswordRecover";
import {MessageService} from "primeng/api";
import {UserService} from "../../services/user.service";
import {ChangePasswordBean} from "../../interfaces/ChangePasswordBean";
import {CacheService} from "../../services/cache.service";
import {exportTranslations} from "../../global-constants";

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent {

  email = ""
  code = ""
  correctCode = false

  newPass = ""
  repeatNewPass = ""

  passShort = false
  passNotEqual = false

  constructor(private cacheService:CacheService,private userService: UserService,private route: ActivatedRoute,private router: Router, private passwordRecoverService: PasswordRecoverService, private messageService: MessageService) {
    // Obtener el valor del parámetro llamado 'codigo' de la URL
    this.route.queryParams.subscribe(params => {

      if (params['email'] == undefined){
        this.router.navigate(['/']);
      } else{
        this.email = atob(params['email']).substring(8);
      }

    });
  }

  ngOnInit() {

  }

  validateCode(){
    const passwordRecover: PasswordRecover = {
      code: this.code,
      email: this.email
    };

    this.passwordRecoverService.isCodeCorrect(passwordRecover).subscribe((data)=>{
      if (data){
        this.correctCode = true
      } else this.errorIncorrecto()
    })
  }

  getExportTranslation(key:string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }


  errorIncorrecto() {
    this.messageService.addAll([{severity:'error', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("CODIGO_INCORRECTO")}]);
  }

  success() {
    this.messageService.addAll([{severity:'success', summary:this.getExportTranslation("ERROR"), detail:this.getExportTranslation("CONTRASENA_CAMBIADA")}]);
  }

  validatePass(){

    if (this.newPass.length < 6){
      this.passShort = true
      return;
    } else this.passShort = false

    if (this.newPass != this.repeatNewPass){
      this.passNotEqual = true
      return;
    } else this.passNotEqual = false

    const changePassword: ChangePasswordBean = {
      password: btoa(this.newPass),
      email: btoa(this.email)
    };

    this.userService.changeUserPassword(changePassword).subscribe((data)=>{
      this.success()
      this.router.navigate(['/']);
    })

  }

}
