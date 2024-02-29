import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { EvaluationPeriodService } from 'src/app/services/evaluation-period.service';
import { EvaluationPeriod } from '../../interfaces/evaluation-period.interface';
import { MatDialog } from '@angular/material/dialog';
import { UtilService } from 'src/app/services/general/util.service';
import { CacheService } from "../../services/cache.service";
import { exportTranslations } from "../../global-constants";
import { QuestionModalComponent } from "../question-modal/question-modal.component";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: 'app-administrar-periodos',
  templateUrl: './administrar-periodos.component.html',
  styleUrls: ['./administrar-periodos.component.scss']
})
export class AdministrarPeriodosComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fechaInicial', 'fechaFinal', 'estado', 'descripcion', 'acciones'];
  dataSource = new MatTableDataSource<EvaluationPeriod>([]);

  constructor(private sanitizer: DomSanitizer, private cacheService: CacheService, private router: Router, private evaluationPeriodService: EvaluationPeriodService, public dialog: MatDialog, private utilService: UtilService) { }  // Inyecta Router en el constructor

  ngOnInit(): void {
    this.loadAllPeriods();
  }

  nuevoPeriodo() {
    this.router.navigate(['/nuevo-periodo']);
  }

  editarPeriodo(row: any) {
    this.router.navigate([`/nuevo-periodo/${row.id}`]);
  }

  sanitizeHtml(html: string) {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  eliminarPeriodo(row: any) {

    const dialogRef = this.dialog.open(QuestionModalComponent, {
      data: {
        title: this.getExportTranslation("CONFIRMACION_ELIMINAR_PERIODO"),
        content: this.sanitizeHtml(this.getExportTranslation("MENSAJE_ELIMINAR_PERIODO")),
      },
      width: '50%',
      height: '60%',
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        // Se hizo clic en "Aceptar" en el modal, realiza la acción deseada.

        this.evaluationPeriodService.delete(row.id).subscribe((data) => {

          if (data) {
            this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_OPERATION") + this.getExportTranslation("EXITO_ELIMINAR_PERIODO"));
            this.loadAllPeriods();
          } else {
            this.utilService.handleError(this.getExportTranslation("ERROR_MODAL_CONTENT") + this.getExportTranslation("ERROR_ELIMINAR_PERIODO"));

          }

        });

      } else {
        // Se hizo clic en "Cancelar" en el modal o el modal se cerró sin acciones, puedes manejarlo aquí si es necesario.
      }
    });


  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

  loadAllPeriods() {
    // Ejemplo de type questionnaire
    /*
    this.evaluationPeriodService.findAll().subscribe((periods: EvaluationPeriod[]) => {
      this.dataSource.data = periods;
    });
    */

    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if(idType !== null) {
      this.evaluationPeriodService.findByQuestionnaireType(idType).subscribe((periods: EvaluationPeriod[]) => {
        this.dataSource.data = periods;
      });
    }
  }
}
