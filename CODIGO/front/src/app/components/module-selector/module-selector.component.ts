import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { CacheService } from "../../services/cache.service";
import { User } from "../../interfaces/user.interface";
import { AuthService } from "../../services/authservice.service";
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';
import { QuestionnaireTypeService } from 'src/app/services/questionnaire-type.service';

@Component({
  selector: 'app-module-selector',
  templateUrl: './module-selector.component.html',
  styleUrls: ['./module-selector.component.scss']
})
export class ModuleSelectorComponent {

  user: User | undefined
  // questionnaireTypes: QuestionnaireType[] = [];
  questionnaireTypes: QuestionnaireType[] = [];

  constructor(private router: Router, public cacheService: CacheService, private authService: AuthService) {
  }

  ngOnInit() {
    // @ts-ignore
    this.user = this.authService.currentUser;
    /*
    this.cacheService.questionnaireTypes$.subscribe((newQuestionnaireTypes: QuestionnaireType[]) => {
      this.questionnaireTypes = newQuestionnaireTypes;
      console.dir('questionnaireTypes', this.questionnaireTypes);
    });
    */
    this.cacheService.loadQuestionnaireTypes().subscribe(data => {
      this.questionnaireTypes = data;
    });
  }

  travel(selectedType: QuestionnaireType): void {
    this.cacheService.selectedTypeValue = selectedType;

    // @ts-ignore
    if (this.user.rol!.includes('ADMINISTRADOR')) {
      this.router.navigate(['gestion']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
      // Redirige a una página por defecto si el rol no coincide con ninguno de los casos
      this.router.navigate(['/home-editor']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }

  /*
  bookTravel(): void {
    this.cacheService.updateModule("book")

    // @ts-ignore
    if (this.user.rol!.includes('ADMINISTRADOR')) {
      this.router.navigate(['gestion']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
      // Redirige a una página por defecto si el rol no coincide con ninguno de los casos
      this.router.navigate(['/home-editor']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
  journalTravel(){
    this.cacheService.updateModule("journal")

    // @ts-ignore
    if (this.user.rol!.includes('ADMINISTRADOR')) {
      this.router.navigate(['gestion']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } else {
      // Redirige a una página por defecto si el rol no coincide con ninguno de los casos
      this.router.navigate(['/home-editor']);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
  */


}
