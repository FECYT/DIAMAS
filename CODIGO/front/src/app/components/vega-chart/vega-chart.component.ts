import { Component } from '@angular/core';
import {
  vegaAraña,
  vegaArañaEN,
  vegaBarras,
  vegaDonut,
  vegaDonutGeneral,
  vegaDonutInteroperabilidad,
  vegaDonutLogs
} from "../../utils/vegaSpecs";
import * as vega from "vega";
import { Spec } from "vega";
import { StatsService } from "../../services/stats.service";
import { FormControl, FormGroup, FormsModule } from "@angular/forms";
import { format, parseISO } from 'date-fns';
import { es } from 'date-fns/locale';
import { ActivatedRoute, Route } from "@angular/router";
import { Stats, StatsDivided } from "../../interfaces/Stats";
import { TranslateService } from "@ngx-translate/core";
import { CatQuestionService } from 'src/app/services/cat-question.service';
import { CatQuestion } from 'src/app/interfaces/cat-question.interface';
import { CacheService } from 'src/app/services/cache.service';


@Component({
  selector: 'app-vega-chart',
  templateUrl: './vega-chart.component.html',
  styleUrls: ['./vega-chart.component.scss']
})
export class VegaChartComponent {

  dateStart: Date | number = 0
  dateEnd: Date | number = 0

  typeLocale = es

  vegaBarras: any;
  vegaAraña: any;
  vegaGeneral: any;
  vegaCategoria1: any;
  vegaCategoria2: any;
  vegaCategoria3: any;
  vegaCategoria4: any;
  vegaCategoria5: any;
  vegaCategoria6: any;
  vegaCategoria7: any;

  datosAdvanced: StatsDivided[] = [];
  datosBasic: StatsDivided[] = [];
  dataArrayBarras: any[] = [];
  dataArrayBarrasEN: any[] = [];
  dataArrayAraña: any[] = [];
  dataArrayArañaEN: any[] = [];

  catList: CatQuestion[] = [];

  generalB = false
  repoB = false
  questionnaireB = false

  repoId = 0
  questionnaireId = 0

  constructor(private cacheService: CacheService, private catQuestionService: CatQuestionService, private statsService: StatsService, private route: ActivatedRoute, private translate: TranslateService) {

    // Obtén los parámetros de la URL
    this.route.queryParams.subscribe(params => {
      const repoId = params['repoId'];
      const questionnaireId = params['questionnaireId'];

      this.repoId = repoId
      this.questionnaireId = questionnaireId

      if (repoId != undefined) {
        this.repoB = true;
      }
      else if (questionnaireId != undefined) {
        this.questionnaireB = true;
      }
      else {
        this.generalB = true;
      }

    });

  }

  ngOnInit(): void {
    this.cacheService.language$.subscribe(lang => {
      // Aquí ejecuta la función que deseas cuando cambia el idioma
      console.log('El idioma ha cambiado:', lang);
      // Llama a tu función aquí

      if (lang === 'es') {
        this.vegaBarras = new vega.View(vega.parse(vegaBarras))
          .renderer('svg')
          .initialize('#vegaBarras');

        this.vegaBarras.data('table', this.dataArrayBarras);
        this.vegaBarras.run();

        this.vegaAraña = new vega.View(vega.parse(vegaAraña))
          .renderer('svg')
          .initialize('#vegaAraña');

        this.vegaAraña.data('table', this.dataArrayAraña);
        this.vegaAraña.run();
      } else {
        this.vegaBarras = new vega.View(vega.parse(vegaBarras))
          .renderer('svg')
          .initialize('#vegaBarras');

        this.vegaBarras.data('table', this.dataArrayBarrasEN);
        this.vegaBarras.run();

        this.vegaAraña = new vega.View(vega.parse(vegaArañaEN))
          .renderer('svg')
          .initialize('#vegaAraña');

        this.vegaAraña.data('table', this.dataArrayArañaEN);
        this.vegaAraña.run();
      }
    });

    const today = new Date();
    const firstDayOfYear = new Date(today.getFullYear(), 0, 1);
    const lastDayOfYear = new Date(today.getFullYear(), 11, 31);

    this.dateStart = firstDayOfYear
    this.dateEnd = lastDayOfYear

    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.catQuestionService.findByQuestionnaireType(idType).subscribe((categoriasList: CatQuestion[]) => {
        this.catList = categoriasList;
        this.checkOption();
      });
    }

    this.vegaBarras = new vega.View(vega.parse(vegaBarras))
      .renderer('svg')
      .initialize('#vegaBarras');

    if (this.cacheService.getLanguage() === 'es') {
      this.vegaAraña = new vega.View(vega.parse(vegaAraña))
        .renderer('svg')
        .initialize('#vegaAraña');
    } else {
      this.vegaAraña = new vega.View(vega.parse(vegaArañaEN))
        .renderer('svg')
        .initialize('#vegaAraña');
    }

    this.vegaGeneral = new vega.View(vega.parse(vegaDonutGeneral))
      .renderer('svg')
      .initialize('#vegaGeneral');

    /*
    this.vegaCategoria1 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria1');

    this.vegaCategoria2 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria2');

    this.vegaCategoria3 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria3');

    this.vegaCategoria4 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria4');

    this.vegaCategoria5 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria5');

    this.vegaCategoria6 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria6');

    this.vegaCategoria7 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria7');
      */


  }

  checkOption() {
    if (this.repoB) this.initRepo()
    else if (this.questionnaireB) this.initQuestionnaire()
    else {
      this.initGeneral()
    }
  }

  onDateChange() {
    this.checkOption()
  }

  initRepo() {
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.statsService.getStatsCategoriasByRepositoryId(idType, this.repoId, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initBarraAraña(data)
      })

      this.statsService.getGeneralStatsByRepositoryId(idType, this.repoId, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initGeneralData(data)
      })

      this.statsService.getStatsByCategoriaAndRepositoryId(idType, this.repoId, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initCategorias(data)
      })
    }

  }

  initQuestionnaire() {
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.statsService.getStatsDividedByQuestionnaire(idType, this.questionnaireId).subscribe((data) => {
        this.initBarraAraña(data)
      })

      this.statsService.getGeneralStatsByQuestionnaireId(idType, this.questionnaireId).subscribe((data) => {
        this.initGeneralData(data)
      })

      this.statsService.getStatsByCategoriaAndQuestionnaireId(idType, this.questionnaireId).subscribe((data) => {
        this.initCategorias(data)
      })
    }
  }

  initGeneral() {
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      this.statsService.getStatsCategoriasDivided(idType, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initBarraAraña(data)
      })

      this.statsService.getGeneralStats(idType, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initGeneralData(data)
      })

      this.statsService.getStatsByCategoria(idType, format(new Date(this.dateStart), "yyyy-MM-dd'T'HH:mm:ss"), format(new Date(this.dateEnd), "yyyy-MM-dd'T'HH:mm:ss")).subscribe((data) => {
        this.initCategorias(data)
      })
    }
  }

  initBarraAraña(data: StatsDivided[]) {

    this.datosAdvanced = data.filter(item => item.type === 'Advanced');
    this.datosBasic = data.filter(item => item.type === 'Basic');

    // Ejemplo de type questionnaire
    // const dataArrayBarras: any[] = [];
    // const dataArrayAraña: any[] = [];
    let promise = new Promise<void>((resolve, reject) => {
      this.catList.forEach((category: CatQuestion) => {

        // Para vegaBarras
        this.dataArrayBarras.push({
          "key": category.categoryType,
          "value": this.datosAdvanced.find(item => item.categoria === category.categoryType)?.puntuacion,
          "category": "Deseado"
        });

        this.dataArrayBarras.push({
          "key": category.categoryType,
          "value": this.datosBasic.find(item => item.categoria === category.categoryType)?.puntuacion,
          "category": "Requerido"
        });

        this.dataArrayBarrasEN.push({
          "key": category.categoryType,
          "value": this.datosAdvanced.find(item => item.categoria === category.categoryType)?.puntuacion,
          "category": "Desired"
        });

        this.dataArrayBarrasEN.push({
          "key": category.categoryType,
          "value": this.datosBasic.find(item => item.categoria === category.categoryType)?.puntuacion,
          "category": "Required"
        });

        // Para vegaAraña
        this.dataArrayAraña.push({
          "category": category.categoryType,
          "value": this.datosAdvanced.find(item => item.categoria === category.categoryType)?.puntuacion,
          "type": "Deseado"
        });

        this.dataArrayAraña.push({
          "category": category.categoryType,
          "value": this.datosBasic.find(item => item.categoria === category.categoryType)?.puntuacion,
          "type": "Requerido"
        });

        // Para vegaAraña
        this.dataArrayArañaEN.push({
          "category": category.categoryType,
          "value": this.datosAdvanced.find(item => item.categoria === category.categoryType)?.puntuacion,
          "type": "Desired"
        });

        this.dataArrayArañaEN.push({
          "category": category.categoryType,
          "value": this.datosBasic.find(item => item.categoria === category.categoryType)?.puntuacion,
          "type": "Required"
        });

      });
      resolve(); // Agrega esta línea para resolver la promesa al final del bucle
    });

    promise.then(() => {
      if (this.cacheService.getLanguage() === 'es') {
        this.vegaBarras.data('table', this.dataArrayBarras);
        this.vegaAraña.data('table', this.dataArrayAraña);
      } else {
        this.vegaBarras.data('table', this.dataArrayBarrasEN);
        this.vegaAraña.data('table', this.dataArrayArañaEN);
      }

      this.vegaAraña.run();
      this.vegaBarras.run();
    });

    /*
    this.vegaAraña.data('table', [
      { "category": this.translate.instant('FUNDING'), "value": datosObligatorios.find(item => item.categoria === 'FUNDING')?.puntuacion },
      { "category": this.translate.instant('GOVERNANCE'), "value": datosObligatorios.find(item => item.categoria === 'GOVERNANCE')?.puntuacion },
      { "category": this.translate.instant('OPEN_SCIENCE'), "value": datosObligatorios.find(item => item.categoria === 'OPEN SCIENCE')?.puntuacion },
      { "category": this.translate.instant('EDITION'), "value": datosObligatorios.find(item => item.categoria === 'EDITION')?.puntuacion },
      { "category": this.translate.instant('TECHNICAL'), "value": datosObligatorios.find(item => item.categoria === 'TECHNICAL')?.puntuacion },
      { "category": this.translate.instant('VISIBILITY'), "value": datosObligatorios.find(item => item.categoria === 'VISIBILITY')?.puntuacion },
      { "category": "EDIB", "value": datosObligatorios.find(item => item.categoria === 'EDIB')?.puntuacion }
    ])
    */
    /*
    this.vegaBarras.data('table', [
      { "key": this.translate.instant('FUNDING'), "value": datosObligatorios.find(item => item.categoria === 'FUNDING')?.puntuacion },
      { "key": this.translate.instant('GOVERNANCE'), "value": datosObligatorios.find(item => item.categoria === 'GOVERNANCE')?.puntuacion },
      { "key": this.translate.instant('OPEN_SCIENCE'), "value": datosObligatorios.find(item => item.categoria === 'OPEN SCIENCE')?.puntuacion },
      { "key": this.translate.instant('EDITION'), "value": datosObligatorios.find(item => item.categoria === 'EDITION')?.puntuacion },
      { "key": this.translate.instant('TECHNICAL'), "value": datosObligatorios.find(item => item.categoria === 'TECHNICAL')?.puntuacion },
      { "key": this.translate.instant('VISIBILITY'), "value": datosObligatorios.find(item => item.categoria === 'VISIBILITY')?.puntuacion },
      { "key": "EDIB", "value": datosObligatorios.find(item => item.categoria === 'EDIB')?.puntuacion }
    ]);
    */

  }

  initGeneralData(data: Stats) {

    this.vegaGeneral = new vega.View(vega.parse(vegaDonutGeneral))
      .renderer('svg')
      .initialize('#vegaGeneral');

    // @ts-ignore
    if (data.puntuacion == 'NaN') data.puntuacion = 0

    this.vegaGeneral.data('table', [{ "category": this.translate.instant('CALIFICACION_GENERAL'), "value": data.puntuacion }]);
    this.vegaGeneral.run();
  }

  initCategorias(data: Stats[]) {

    /*
    this.vegaCategoria1 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria1');

    this.vegaCategoria2 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria2');

    this.vegaCategoria3 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria3');

    this.vegaCategoria4 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria4');

    this.vegaCategoria5 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria5');

    this.vegaCategoria6 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria6');

    this.vegaCategoria7 = new vega.View(vega.parse(vegaDonut))
      .renderer('svg')
      .initialize('#vegaCategoria7');

    this.vegaCategoria1.data('table', [{ "category": this.translate.instant('FUNDING'), "value": data.find(item => item.categoria === 'FUNDING')?.puntuacion }]);
    this.vegaCategoria2.data('table', [{ "category": this.translate.instant('GOVERNANCE'), "value": data.find(item => item.categoria === 'GOVERNANCE')?.puntuacion }]);
    this.vegaCategoria3.data('table', [{ "category": this.translate.instant('OPEN_SCIENCE'), "value": data.find(item => item.categoria === 'OPEN SCIENCE')?.puntuacion }]);
    this.vegaCategoria4.data('table', [{ "category": this.translate.instant('EDITION'), "value": data.find(item => item.categoria === "EDITION")?.puntuacion }]);
    this.vegaCategoria5.data('table', [{ "category": this.translate.instant('TECHNICAL'), "value": data.find(item => item.categoria === 'TECHNICAL')?.puntuacion }]);
    this.vegaCategoria6.data('table', [{ "category": this.translate.instant('VISIBILITY'), "value": data.find(item => item.categoria === 'VISIBILITY')?.puntuacion }]);
    this.vegaCategoria7.data('table', [{ "category": "EDIB", "value": data.find(item => item.categoria === 'EDIB')?.puntuacion }]);

    this.vegaCategoria1.run()
    this.vegaCategoria2.run()
    this.vegaCategoria3.run()
    this.vegaCategoria4.run()
    this.vegaCategoria5.run()
    this.vegaCategoria6.run()
    this.vegaCategoria7.run()
    */

    const vegaViews: vega.View[] = [];
    const vegaDataArray: any[] = [];
    const vegaContainer = document.getElementById('vegaContainer');


    this.catList.forEach((category: CatQuestion, index) => {
      // Crear un nuevo div para cada vegaCategoria
      const vegaDiv = document.createElement('div');
      vegaDiv.style.width = '320px';
      vegaDiv.style.marginRight = '20px';
      vegaDiv.classList.add('floatingGeneral', 'mb-3');
      vegaDiv.id = `vegaCategoria${index + 1}`;

      // Agregar el div al contenedor general
      vegaContainer!.appendChild(vegaDiv);

      // Crear la instancia de vega.View
      const vegaCategoria = new vega.View(vega.parse(vegaDonut))
        .renderer('svg')
        .initialize(`#vegaCategoria${index + 1}`); // Aseguramos que el índice sea 1-indexed
      vegaViews.push(vegaCategoria);
      vegaDataArray.push({
        "category": category.categoryType,
        "value": (data.find(item => item.categoria === category.categoryType)?.puntuacion ?? 0).toFixed(2)
      });
    });

    vegaViews.forEach((view, index) => {
      view.data('table', [vegaDataArray[index]]);
      view.run();
    });

  }
  
}
