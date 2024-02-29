import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Usuario } from './components/login/models/usuario.model';
import { AuthService } from './services/authservice.service';
import { User } from './interfaces/user.interface';
import {PrimeNGConfig} from "primeng/api";
import {CacheService} from "./services/cache.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'evaluacion-repositorios';
  usuario: User | null = null;

  constructor(private cacheService:CacheService,private translateService: TranslateService, private authService: AuthService,private config: PrimeNGConfig) {

  }

  ngOnInit(): void {

    this.initLanguage()

    this.translateService.setDefaultLang(this.cacheService.getLanguage());
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));

    this.authService.currentUser$.subscribe(user => {
        this.usuario = user;
    });

  }

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }

  initLanguage(){

    if (this.cacheService.getLanguage() == "" || this.cacheService.getLanguage() == undefined){

      this.cacheService.setLanguage("en")

    }

  }

}
