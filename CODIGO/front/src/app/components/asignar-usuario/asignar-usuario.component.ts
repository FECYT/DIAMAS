import { Component, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedDataService } from 'src/app/services/shared-data.service';
import { AuthService } from 'src/app/services/authservice.service';
import { User } from 'src/app/interfaces/user.interface';
import { AssignedEvaluatorService } from 'src/app/services/assigned-evaluator.service';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { UserService } from 'src/app/services/user.service';
import { FormControl } from '@angular/forms';
import { DnetService } from "../../services/dnet.service";
import { Location } from '@angular/common';
import { CacheService } from "../../services/cache.service";
import { MatAutocomplete, MatAutocompleteDefaultOptions, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MessageService } from 'primeng/api';
import { Observable, map, startWith } from 'rxjs';
import {exportTranslations} from "../../global-constants";

export interface Evaluacion {
  nombre: string;
  ultimaModificacion: string;
  evaluacion: string;
  responsableRepositorio: string | null;
}

@Component({
  selector: 'app-asignar-usuario',
  templateUrl: './asignar-usuario.component.html',
  styleUrls: ['./asignar-usuario.component.scss']
})
export class AsignarUsuarioComponent {
  evaluators: User[] = [];
  selectedEvaluadores: User[] = [];
  filteredEvaluadores: Observable<User[]> | undefined;
  nombreRepositorio: string = '';
  idEvaluacion: number = 0;
  edit: boolean = false;
  loading = false

  evaluacion: any;
  lastFilter: string = '';

  // El control para el filtro
  filterControl = new FormControl();
  @ViewChild('auto') matAutocompleteUser!: MatAutocomplete;

  constructor(private route: ActivatedRoute, private cacheService: CacheService, private location: Location, private dnetService: DnetService, private sharedService: SharedDataService, private router: Router, private authService: AuthService, private assignedEvaluatorService: AssignedEvaluatorService, private userService: UserService, private dialog: MatDialog, private sanitizer: DomSanitizer, private messageService: MessageService) {
    this.route.queryParams.subscribe(params => {
      this.nombreRepositorio = decodeURIComponent(params['nombreRepositorio']);
    });

    const navigation = this.router.getCurrentNavigation();

    if (navigation && navigation.extras.state && navigation.extras.state['evaluacion']) {
      this.evaluacion = navigation.extras.state['evaluacion'];
    } else this.location.back();
  }

  ngOnInit(): void {
/*    this.filteredEvaluadores = this.filterControl.valueChanges.pipe(
      startWith<string | User[]>(''),
      map(value => typeof value === 'string' ? value : this.lastFilter),
      map(filter => this.filter(filter))
    );
    // Obtener parámetros
    this.nombreRepositorio = this.evaluacion.repository.nombreDnet;
    this.idEvaluacion = this.evaluacion.id;

    this.loading = true

    this.checkEvaluatorList();

    if (this.evaluacion.users && this.evaluacion.users.length > 0) {
      this.selectedEvaluadores = this.evaluacion.users;
      this.filteredEvaluadores.subscribe(evaluadores => {
        evaluadores.forEach(evaluator => {
          const isSelected = this.selectedEvaluadores.some(selected => selected.userName!.toLowerCase() === evaluator.userName!.toLowerCase());
          evaluator.selected = isSelected;
        });
      });

      this.edit = true;
    }*/
  }

  ngAfterViewInit() {
    this.matAutocompleteUser.hideSingleSelectionIndicator = true;
  }

  checkEvaluatorList(): void {
    const evaluators = this.cacheService.getEvaluatorList();
    if (evaluators != null) {
      this.evaluators = evaluators
      // this.filteredEvaluators = this.evaluators;
      this.loading = false;

      this.filterControl.setValue('');
    } else {
      setTimeout(() => {
        this.checkEvaluatorList();
      }, 1000); // Revisar de nuevo en 1 segundo (1000 milisegundos)
    }
  }

  guardar() {
    if (this.edit) {
      this.assignedEvaluatorService.editAssignedEvaluators(this.idEvaluacion, this.selectedEvaluadores).subscribe({
        next: (response) => {
          // Redirecciona al usuario una vez que los datos se guarden correctamente
          this.router.navigate(['/listaEvaluacionesActiva']);
        },
        error: (error) => {
          // Mostrar modal de error
          this.dialog.open(SuccessModalComponent, {
            data: {
              title: 'Error',
              content: this.sanitizeHtml(this.getExportTranslation("ERROR_MODAL_CONTENT")),
            },
            width: '70%',
            height: '70%'
          });
        }
      });
    } else {
      this.assignedEvaluatorService.assignEvaluatorsToEvaluation(this.idEvaluacion, this.selectedEvaluadores).subscribe({
        next: (response) => {
          // Redirecciona al usuario una vez que los datos se guarden correctamente
          this.router.navigate(['/listaEvaluacionesActiva']);
        },
        error: (error) => {
          // Mostrar modal de error
          this.dialog.open(SuccessModalComponent, {
            data: {
              title: 'Error',
              content: this.sanitizeHtml(this.getExportTranslation("ERROR_MODAL_CONTENT")),
            },
            width: '70%',
            height: '70%'
          });
        }
      });
    }
  }

  getExportTranslation(key:string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  // Función para filtrar evaluadores
  filter(filter: string): User[] {
/*    this.lastFilter = filter;
    if (filter) {
      return this.evaluators.filter(option => {
        return option.userName!.toLowerCase().indexOf(filter.toLowerCase()) >= 0;
      })
    } else {
      return this.evaluators.slice();
    }*/
    return [];
  }

  optionClicked(event: Event, evaluator: User) {
    event.stopPropagation();
    this.toggleSelection(evaluator);
  }

  toggleSelection(evaluator: User) {
/*    if (this.selectedEvaluadores.length === 2) {
      if (evaluator.selected === false) {
        this.messageService.addAll([{ severity: 'error', summary: ERROR, detail: ASSIGN_EVALUATOR_ERROR }]);
      } else {
        const i = this.selectedEvaluadores.findIndex(value => value.userName!.toLowerCase() === evaluator.userName!.toLowerCase());
        this.selectedEvaluadores.splice(i, 1);
        setTimeout(() => {
          evaluator.selected = false;
        }, 0)
      }
    } else {
      if (evaluator.selected === false) {
        this.selectedEvaluadores.push(evaluator);
        evaluator.selected = true;
      } else {
        const i = this.selectedEvaluadores.findIndex(value => value.userName!.toLowerCase() === evaluator.userName!.toLowerCase());
        this.selectedEvaluadores.splice(i, 1);
        evaluator.selected = false;
      }
    }*/
  }
}
