import { Component, NgZone } from '@angular/core';
import { roles } from "../../utils/Roles";
import { UserService } from "../../services/user.service";
import { User } from "../../interfaces/user.interface";
import { StatsService } from "../../services/stats.service";
import { SelectItem } from "primeng/api";
import { EvaluationPeriodService } from "../../services/evaluation-period.service";
import { EvaluationPeriod } from "../../interfaces/evaluation-period.interface";
import { forkJoin, map, switchMap } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { CacheService } from "../../services/cache.service";
import * as XLSX from 'xlsx';
import { InstitucionService } from "../../services/institucion-service";
import {CatQuestionService} from "../../services/cat-question.service";

@Component({
  selector: 'app-herramienta-comparativa',
  templateUrl: './herramienta-comparativa.component.html',
  styleUrls: ['./herramienta-comparativa.component.scss']
})
export class HerramientaComparativaComponent {

  users: User[] = []
  selectedUsers: User[] = []

  maxSelection = 4;

  secciones = [''];
  nombresUser: String[] = ['Usuario', 'Usuario', 'Usuario', 'Usuario']
  acronym = ""

  columnas: string[] = ['seccion', 'usuario1', 'usuario2', 'usuario3', 'usuario4'];
  dataSource: any[] = [];

  evaluationPeriods: EvaluationPeriod[] = [
  ];

  selectedEvaluation: EvaluationPeriod | undefined;

  constructor(private catQuestionService:CatQuestionService,private institutionService: InstitucionService, private cacheService: CacheService, private translate: TranslateService, private userService: UserService, private statsService: StatsService, private evaluationPeriodService: EvaluationPeriodService) {
  }

  llenarDatos(initialData?: any[]) {


    const datos: any[] = [];

    if (initialData == undefined) {

      this.nombresUser = [this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO')]


      // Añade filas con datos de usuario
      for (let i = 0; i < this.secciones.length; i++) {
        const fila = {
          seccion: i < 0 ? '' : this.secciones[i],
          usuario1: i === 0 ? this.acronym : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
          usuario2: i === 0 ? this.acronym : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
          usuario3: i === 0 ? this.acronym : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
          usuario4: i === 0 ? this.acronym : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
        };
        datos.push(fila);
      }
    } else {

      this.nombresUser = [this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO')]

      for (let i = 0; i < initialData.length; i++) {
        this.nombresUser[i] = initialData[i].nombre;
      }

      // Añade filas con datos de usuario
      for (let i = 0; i < this.secciones.length; i++) {

        let fila = {}

        switch (initialData.length) {

          case 1:
            fila = {
              seccion: i < 0 ? '' : this.secciones[i],
              usuario1: i === 0 ? initialData[0].institucion : i === 1 ? initialData[0].stats[0].puntuacion + ' %' : i === 2 ? initialData[0].stats[1].puntuacion + ' %' : i === 3 ? initialData[0].stats[2].puntuacion + ' %' : i === 4 ? initialData[0].stats[3].puntuacion + ' %' : i === 5 ? initialData[0].stats[4].puntuacion + ' %' : i === 6 ? initialData[0].stats[5].puntuacion + ' %' : i === 7 ? initialData[0].stats[6].puntuacion + ' %' : i === 8 ? initialData[0].stats[7].puntuacion + ' %' : 0,
              usuario2: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
              usuario3: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
              usuario4: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %'
            };
            break;
          case 2:
            fila = {
              seccion: i < 0 ? '' : this.secciones[i],
              usuario1: i === 0 ? initialData[0].institucion : i === 1 ? initialData[0].stats[0].puntuacion + ' %' : i === 2 ? initialData[0].stats[1].puntuacion + ' %' : i === 3 ? initialData[0].stats[2].puntuacion + ' %' : i === 4 ? initialData[0].stats[3].puntuacion + ' %' : i === 5 ? initialData[0].stats[4].puntuacion + ' %' : i === 6 ? initialData[0].stats[5].puntuacion + ' %' : i === 7 ? initialData[0].stats[6].puntuacion + ' %' : i === 8 ? initialData[0].stats[7].puntuacion + ' %' : 0,
              usuario2: i === 0 ? initialData[1].institucion : i === 1 ? initialData[1].stats[0].puntuacion + ' %' : i === 2 ? initialData[1].stats[1].puntuacion + ' %' : i === 3 ? initialData[1].stats[2].puntuacion + ' %' : i === 4 ? initialData[1].stats[3].puntuacion + ' %' : i === 5 ? initialData[1].stats[4].puntuacion + ' %' : i === 6 ? initialData[1].stats[5].puntuacion + ' %' : i === 7 ? initialData[1].stats[6].puntuacion + ' %' : i === 8 ? initialData[1].stats[7].puntuacion + ' %' : 0,
              usuario3: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
              usuario4: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
            };
            break;
          case 3:
            fila = {
              seccion: i < 0 ? '' : this.secciones[i],
              usuario1: i === 0 ? initialData[0].institucion : i === 1 ? initialData[0].stats[0].puntuacion + ' %' : i === 2 ? initialData[0].stats[1].puntuacion + ' %' : i === 3 ? initialData[0].stats[2].puntuacion + ' %' : i === 4 ? initialData[0].stats[3].puntuacion + ' %' : i === 5 ? initialData[0].stats[4].puntuacion + ' %' : i === 6 ? initialData[0].stats[5].puntuacion + ' %' : i === 7 ? initialData[0].stats[6].puntuacion + ' %' : i === 8 ? initialData[0].stats[7].puntuacion + ' %' : 0,
              usuario2: i === 0 ? initialData[1].institucion : i === 1 ? initialData[1].stats[0].puntuacion + ' %' : i === 2 ? initialData[1].stats[1].puntuacion + ' %' : i === 3 ? initialData[1].stats[2].puntuacion + ' %' : i === 4 ? initialData[1].stats[3].puntuacion + ' %' : i === 5 ? initialData[1].stats[4].puntuacion + ' %' : i === 6 ? initialData[1].stats[5].puntuacion + ' %' : i === 7 ? initialData[1].stats[6].puntuacion + ' %' : i === 8 ? initialData[1].stats[7].puntuacion + ' %' : 0,
              usuario3: i === 0 ? initialData[2].institucion : i === 1 ? initialData[2].stats[0].puntuacion + ' %' : i === 2 ? initialData[2].stats[1].puntuacion + ' %' : i === 3 ? initialData[2].stats[2].puntuacion + ' %' : i === 4 ? initialData[2].stats[3].puntuacion + ' %' : i === 5 ? initialData[2].stats[4].puntuacion + ' %' : i === 6 ? initialData[2].stats[5].puntuacion + ' %' : i === 7 ? initialData[2].stats[6].puntuacion + ' %' : i === 8 ? initialData[2].stats[7].puntuacion + ' %' : 0,
              usuario4: i === 0 ? this.translate.instant('ACRONIMO_INSTITUCION') : i === 1 ? '0 %' : i === 2 ? '0 %' : i === 3 ? '0 %' : i === 4 ? '0 %' : i === 5 ? '0 %' : i === 6 ? '0 %' : i === 7 ? '0 %' : i === 8 ? '0 %' : '0 %',
            };
            break;
          case 4:
            fila = {
              seccion: i < 0 ? '' : this.secciones[i],
              usuario1: i === 0 ? initialData[0].institucion : i === 1 ? initialData[0].stats[0].puntuacion + ' %' : i === 2 ? initialData[0].stats[1].puntuacion + ' %' : i === 3 ? initialData[0].stats[2].puntuacion + ' %' : i === 4 ? initialData[0].stats[3].puntuacion + ' %' : i === 5 ? initialData[0].stats[4].puntuacion + ' %' : i === 6 ? initialData[0].stats[5].puntuacion + ' %' : i === 7 ? initialData[0].stats[6].puntuacion + ' %' : i === 8 ? initialData[0].stats[7].puntuacion + ' %' : 0,
              usuario2: i === 0 ? initialData[1].institucion : i === 1 ? initialData[1].stats[0].puntuacion + ' %' : i === 2 ? initialData[1].stats[1].puntuacion + ' %' : i === 3 ? initialData[1].stats[2].puntuacion + ' %' : i === 4 ? initialData[1].stats[3].puntuacion + ' %' : i === 5 ? initialData[1].stats[4].puntuacion + ' %' : i === 6 ? initialData[1].stats[5].puntuacion + ' %' : i === 7 ? initialData[1].stats[6].puntuacion + ' %' : i === 8 ? initialData[1].stats[7].puntuacion + ' %' : 0,
              usuario3: i === 0 ? initialData[2].institucion : i === 1 ? initialData[2].stats[0].puntuacion + ' %' : i === 2 ? initialData[2].stats[1].puntuacion + ' %' : i === 3 ? initialData[2].stats[2].puntuacion + ' %' : i === 4 ? initialData[2].stats[3].puntuacion + ' %' : i === 5 ? initialData[2].stats[4].puntuacion + ' %' : i === 6 ? initialData[2].stats[5].puntuacion + ' %' : i === 7 ? initialData[2].stats[6].puntuacion + ' %' : i === 8 ? initialData[2].stats[7].puntuacion + ' %' : 0,
              usuario4: i === 0 ? initialData[3].institucion : i === 1 ? initialData[3].stats[0].puntuacion + ' %' : i === 2 ? initialData[3].stats[1].puntuacion + ' %' : i === 3 ? initialData[3].stats[2].puntuacion + ' %' : i === 4 ? initialData[3].stats[3].puntuacion + ' %' : i === 5 ? initialData[3].stats[4].puntuacion + ' %' : i === 6 ? initialData[3].stats[5].puntuacion + ' %' : i === 7 ? initialData[3].stats[6].puntuacion + ' %' : i === 8 ? initialData[3].stats[7].puntuacion + ' %' : 0,
            };
            break;

        }

        datos.push(fila);
      }

    }

    this.dataSource = [...datos];

    return datos;
  }

  ngOnInit() {

    setTimeout(() => {
      this.initTraduccionesDelay();

    }, 80);

    this.initTraducciones()



    this.userService.getUsers().subscribe((data) => {

      this.users = data.map((user) => ({
        ...user,
        displayName: `${user.nombre} ${user.apellidos} [${user.email}]`
      }));

    })

    const idType = this.cacheService.selectedTypeId;

    this.evaluationPeriodService.findAll().subscribe((data) => {
      this.evaluationPeriods = data.filter(item => item.questionnaireType.id === idType);
    })

  }

  initTraduccionesDelay() {
    this.nombresUser = [this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO'), this.translate.instant('USUARIO')]
  }

  initTraducciones() {

    this.secciones = ['']

    this.catQuestionService.findByQuestionnaireType(this.cacheService.selectedTypeId!!).subscribe((data)=>{

      data.forEach((item) => {
        // Reemplazar espacios por guiones bajos en item.categoryType
        const categoryTypeFormatted = item.categoryType?.replace(/\s+/g, '_') || '';

        // Agregar al arreglo this.secciones
        this.secciones.push(this.translate.instant(categoryTypeFormatted));
      });

      this.secciones.push('General')
      this.acronym = this.translate.instant('ACRONIMO_INSTITUCION')

      //Una vez cargada las categorías relleno con datos
      this.dataSource = this.llenarDatos();

    })

  }

  onMultiSelectChange(event: any) {
    // Verificar si se supera el límite de selección
    if (this.selectedUsers.length > this.maxSelection) {
      // Truncar la selección a los primeros 4 elementos
      this.selectedUsers = this.selectedUsers.slice(0, this.maxSelection);
    } else {
      if (this.selectedEvaluation != undefined) {

        this.fetchData()

      }

    }
  }


  onDropdownChange(event: any) {
    if (this.selectedUsers.length != 0) {
      this.fetchData()
    }
  }

  fetchData() {
    const observables = this.selectedUsers.map(user => {
      const idType = this.cacheService.selectedTypeId;
      if (idType !== null) {
        return this.statsService.getStatsByUserAndPeriodId(idType, this.selectedEvaluation!!.id, user.id!!)
          .pipe(
            switchMap(statsData => {
              return this.institutionService.findByUserId(user.id!!)
                .pipe(
                  map(institutionData => ({
                    id: user.id,
                    nombre: user.nombre,
                    institucion: institutionData ? institutionData.acronimo || '' : '',
                    stats: statsData
                  }))
                );
            })
          );
      } else {
        return null;
      }
    });


    forkJoin(observables).subscribe(userData => {
      this.llenarDatos(userData);
    });

    if (observables.length === 0) {
      this.llenarDatos()
    }


  }

  downloadExcel() {
    const fileName = "DIAMAS - Comparator";
    const sheetName = "Users";

    // Crear una copia profunda de los datos sin afectar el front-end ni el orden
    let dataCopy = JSON.parse(JSON.stringify(this.dataSource));


    // Crear la hoja de cálculo
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(dataCopy);

    // Buscar y reemplazar en la hoja de cálculo
    if (this.selectedUsers[0]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario1') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[0].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[1]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario2') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[1].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[2]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario3') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[2].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[3]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario4') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[3].nombre;
          }
        });
      });
    }


    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);

    // Descargar el archivo
    XLSX.writeFile(wb, `${fileName}.xlsx`);
  }



  downloadCSV() {

    const fileName = "DIAMAS - Comparator";
    const sheetName = "Users";

    // Crear una copia profunda de los datos sin afectar el front-end ni el orden
    let dataCopy = JSON.parse(JSON.stringify(this.dataSource));


    // Crear la hoja de cálculo
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(dataCopy);

    // Buscar y reemplazar en la hoja de cálculo
    if (this.selectedUsers[0]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario1') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[0].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[1]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario2') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[1].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[2]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario3') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[2].nombre;
          }
        });
      });
    }

    if (this.selectedUsers[3]) {
      XLSX.utils.sheet_to_json(ws, { header: 1 }).forEach((row, rowIndex) => {
        // @ts-ignore
        row.forEach((cell, cellIndex) => {
          if (cell === 'usuario4') {
            ws[XLSX.utils.encode_cell({ r: rowIndex, c: cellIndex })].v = this.selectedUsers[3].nombre;
          }
        });
      });
    }


    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);

    // Convertir la hoja de cálculo a CSV
    const csvData = XLSX.utils.sheet_to_csv(ws);

    // Crear y descargar el archivo CSV
    const csvBlob = new Blob([csvData], { type: 'text/csv;charset=utf-8' });
    const csvUrl = window.URL.createObjectURL(csvBlob);
    const csvLink = document.createElement('a');
    csvLink.href = csvUrl;
    csvLink.setAttribute('download', fileName);
    document.body.appendChild(csvLink);
    csvLink.click();
    document.body.removeChild(csvLink);
  }


}
