import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-recaptcha',
  templateUrl: './recaptcha.component.html',
})
export class RecaptchaComponent {
  @Output() captchaResponse: EventEmitter<string> = new EventEmitter<string>();

  resolved(captchaResponse:string) {
    // Aquí puedes obtener la respuesta del captcha de la forma correcta, según la biblioteca que estés utilizando
    this.captchaResponse.emit(captchaResponse);
  }

}
