import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Subject, map, takeUntil } from 'rxjs';
import { CatQuestionService } from 'src/app/services/cat-question.service';
import { QuestionService } from 'src/app/services/question.service';
import { SuccessModalComponent } from '../success-modal/success-modal.component';
import { CatQuestion } from 'src/app/interfaces/cat-question.interface';
import { MatSort } from '@angular/material/sort';
import { ViewChild, AfterViewInit } from '@angular/core';
import { UtilService } from 'src/app/services/general/util.service';
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";

@Component({
  selector: 'app-lista-terminos',
  templateUrl: './lista-terminos.component.html',
  styleUrls: ['./lista-terminos.component.scss']
})
export class ListaTerminosComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['nombre', 'tooltip', 'orden', 'acciones'];
  dataSource: CatQuestion[] = [];  // Inicialmente vacío, del tipo que devuelve tu API


  private destroy$ = new Subject<void>();

  constructor(private cacheService: CacheService, private catQuestionService: CatQuestionService, private router: Router, private dialog: MatDialog, private sanitizer: DomSanitizer, private questionService: QuestionService, private utilService: UtilService) { }

  ngOnInit(): void {

    this.loadAllCategories();

  }
  /**
   * Método que carga todas las categorías ordenadas de manera ascendente
   */
  loadAllCategories(): void {
    // Ejemplo de type questionnaire
    /*
    this.catQuestionService.findAll()
      .pipe(
        map((data: any[]) => data.filter(cat => cat.ndeleteState === 1)),
        takeUntil(this.destroy$)
      )
      .subscribe(
        data => {

          const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;

          if (idType == 2){
            this.dataSource = data.filter(item => item.id >= 0 && item.id <= 7);
          } else this.dataSource = data.filter(item => item.id >= 8 && item.id <= 15);

          // Ordenar dataSource por el campo "orden" de manera ascendente
          this.dataSource.sort((a, b) => a.orden - b.orden);
        },
        error => {
          console.error('Error al cargar las categorías', error);
          this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT")+this.getExportTranslation("ERROR_CARGAR_CATEGORIAS"));
        }
      );
      */
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.catQuestionService.findByQuestionnaireType(idType).pipe(
        map((data: any[]) => data.filter(cat => cat.ndeleteState === 1)),
        takeUntil(this.destroy$)
      )
        .subscribe(
          data => {
            this.dataSource = data;

            // Ordenar dataSource por el campo "orden" de manera ascendente
            this.dataSource.sort((a, b) => a.orden - b.orden);
          },
          error => {
            console.error('Error al cargar las categorías', error);
            this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_CARGAR_CATEGORIAS"));
          }
        );
    }
  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }


  agregarTermino(): void {
    this.router.navigate(['/agregarTermino'], { state: { modulo: this.cacheService.retrieveModuleFromStorage() } });
  }

  /**
   * Método para eliminar una categoría después de verificar si tiene preguntas asociadas.
   * @param categoryId - El ID de la categoría a eliminar.
   */
  deleteCategory(categoryId: number): void {
    // Primero verifica si la categoría tiene preguntas asociadas
    this.catQuestionService.findById(categoryId)
      .pipe(takeUntil(this.destroy$))
      .subscribe(category => {
        if (category.hasQuestion) {
          // Si hay preguntas asociadas, muestra un mensaje al usuario.
          //alert('No es posible eliminar esta categoría porque tiene preguntas asociadas.');
          this.utilService.handleInfo(this.getExportTranslation("INFO_CATEGORIAS"));
        } else {

          // Si no hay preguntas asociadas, procede a eliminar la categoría.
          this.catQuestionService.delete(categoryId)
            .pipe(takeUntil(this.destroy$))
            .subscribe({
              next: () => {
                this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("EXITO_ELIMINAR_CATEGORIA"));
                this.loadAllCategories();
              },
              error: error => {
                console.error('Error al eliminar la categoría', error);
                this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_ELIMINAR_CATEGORIA"));
              }
            });
        }
      }, error => {
        console.error('Error al verificar si la categoría tiene preguntas asociadas', error);
        this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("MENSAJE_VERIFICAR_CATEGORIA"));
      });

  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  ngOnDestroy(): void {
    this.destroy$.next();  // Emite un valor, lo que hace que cualquier observador que utilice takeUntil(this.destroy$) se complete
    this.destroy$.complete();  // Marca el subject como completado
  }

  editarTermino(row: any): void {
    this.router.navigate([`/agregarTermino/${row.id}`], { state: { modulo: this.cacheService.retrieveModuleFromStorage() } });  // Para editar
  }
}
