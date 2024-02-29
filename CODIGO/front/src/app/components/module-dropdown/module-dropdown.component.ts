import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CacheService } from "../../services/cache.service";
import { QuestionnaireType } from 'src/app/interfaces/questionnaire-type.interface';
import {AuthService} from "../../services/authservice.service";

@Component({
  selector: 'app-module-dropdown',
  templateUrl: './module-dropdown.component.html',
  styleUrls: ['./module-dropdown.component.scss']
})
export class ModuleDropdownComponent implements OnInit {
  selectedType: QuestionnaireType | null = null;
  questionnaireTypes: QuestionnaireType[] = [];

  show = true

  constructor(public cacheService: CacheService, private authService:AuthService) { }

  ngOnInit() {

    if (!this.authService.currentUser!!.rol!!.includes("ADMINISTRADOR")){
      this.show = false
      return
    }

    // Cargar el array de questionnaireTypes al iniciar el componente
    this.questionnaireTypes = this.cacheService.questionnaireTypesValue;

    // Obtener el selectedTypeValue y seleccionarlo si existe
    const selectedTypeFromCache = this.cacheService.selectedTypeValue;

    // Buscar el objeto correspondiente en la lista y establecer la referencia
    if (selectedTypeFromCache) {
      this.selectedType = this.questionnaireTypes.find(type => type.id === selectedTypeFromCache.id) || null;
    }
  }

  onSelectChange(): void {
    // Actualizar el selectedTypeValue en el servicio
    this.cacheService.selectedTypeValue = this.selectedType;

    // Recargar la página
    window.location.reload();
  }

}

