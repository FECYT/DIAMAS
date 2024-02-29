import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Usuario } from 'src/app/components/login/models/usuario.model';
import { AuthService } from 'src/app/services/authservice.service';
import { Subscription } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import {UtilService} from "../../services/general/util.service";
import {ConfigService} from "../../services/config-service";
import {TranslateService} from "@ngx-translate/core";
import {PrimeNGConfig} from "primeng/api";
import {CacheService} from "../../services/cache.service";
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  usuario: User | null = null;
  private userSubscription!: Subscription;
  mostrarDropdown = false
  mostrarDropdownResponsableRepo = false
  mostrarDropdownRegistro = false
  mostrarDropdownValidacion = false
  mostrarDropdownAbout = false;
  loginRoute = ""
  loginExternal = false
  mostrarEstadisticas = false

  language = "es"

  activated = false

  cachedVariable: string | undefined;
  private subscription: Subscription;

  constructor(private cacheService:CacheService,private config: PrimeNGConfig,private translateService:TranslateService,private authService: AuthService,private router: Router, private utilService:UtilService, private configService:ConfigService, private route: ActivatedRoute) {

    this.subscription = this.cacheService.selectedType$.subscribe((selectedType) => {
      // Lógica para recargar la parte del código que deseas actualizar
      this.updateStatus(selectedType);
    });

  }

  ngOnInit(): void {

    this.language = this.cacheService.getLanguage()

    this.configService.getLoginExternal().subscribe((loginExternal=>{this.loginExternal = loginExternal}))
    this.configService.getLoginRoute().subscribe((loginRoute=>{this.loginRoute = loginRoute}))

    this.userSubscription = this.authService.currentUser$.subscribe(user => {
      this.usuario = user;
    });

  }

  updateStatus(selectedType: QuestionnaireType | null): void {
    if (selectedType) {
      this.activated = true;
    } else {
      this.activated = false;
    }
  }

  languageChange(lang:any){
    this.language = lang.target.value

    this.cacheService.setLanguage(lang.target.value)
    this.translate(lang.target.value)
  }

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe((res) => this.config.setTranslation(res));
  }

  logout() {
      sessionStorage.removeItem('module')
      // this.cacheService.updateModule('')
      this.cacheService.clearData();
      this.authService.logout();
  }


  navigate(urlTarget:string, openInNewTab:Boolean = false){

    this.utilService.travelToFecyt(urlTarget,openInNewTab)

    //TODO BORRAR ESTO, SOLO PARA DEVELOPMENT
    //this.router.navigate(['/in-between-traveller']);
  }

  ngOnDestroy(): void {
      // Desuscribirse del observable para evitar fugas de memoria
      if (this.userSubscription) {
          this.userSubscription.unsubscribe();
      }
  }

  protected readonly console = console;
}
