import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-captcha',
  templateUrl: './captcha.component.html',
  styleUrls: ['./captcha.component.scss'],
})
export class CaptchaComponent implements OnInit {
  captcha: string = '';
  userCaptcha: string = '';

  ngOnInit() {
    this.generateCaptcha();
  }

  generateCaptcha() {
    const captchaLength = 6;
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let captcha = '';
    for (let i = 0; i < captchaLength; i++) {
      captcha += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    this.captcha = captcha;
  }

  validateCaptcha() {
    if (this.userCaptcha === this.captcha) {
      alert('Captcha válido. Formulario enviado.');
    } else {
      alert('Captcha incorrecto. Inténtalo de nuevo.');
      this.generateCaptcha();
    }
  }
}

