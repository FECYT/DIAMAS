import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import {EMPTY, map, Observable, of, tap} from 'rxjs';
import { environment } from 'src/environments/environment';
import { Questionnaire } from '../interfaces/questionnaire.interface';
import { DatePipe } from '@angular/common';
import { AuthService } from "./authservice.service";
import { vegaArañaPDF, vegaArañaPDFEN } from "../utils/vegaSpecs";
import { jsPDF } from "jspdf";
// @ts-ignore
import html2canvas from 'html2canvas';
import * as vega from 'vega';
import { StatsService } from 'src/app/services/stats.service';
import { TranslateService } from '@ngx-translate/core';
import { saveAs } from 'file-saver';
import JSZip from 'jszip';
import { CacheService } from './cache.service';
import { UtilService } from './general/util.service';
import { Router } from '@angular/router';
import { exportTranslations } from "../global-constants";
import * as pdfjsLib from 'pdfjs-dist';
import {pdfToPng, PngPageOutput} from "pdf-to-png-converter";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireService {
  private apiUrl = environment.host + 'api/questionnaire/'; // Ajusta la URL de tu API para Questionnaire

  constructor(private router: Router, private cacheService: CacheService, private translate: TranslateService, private statsService: StatsService, private http: HttpClient, private datePipe: DatePipe, private authService: AuthService, private utilService: UtilService) {


  }


  create(questionnaire: Questionnaire): Observable<Questionnaire> {
    return this.http.post<Questionnaire>(this.apiUrl + 'create', questionnaire);
  }

  update(questionnaire: Questionnaire): Observable<Questionnaire> {
    const url = `${this.apiUrl}update`;
    return this.http.put<Questionnaire>(url, questionnaire);
  }

  sendQuestionnaire(evaluationId: number): Observable<void> {
    const url = `${this.apiUrl}sendQuestionnaire/evaluation/${evaluationId}`;
    return this.http.put<void>(url, null);
  }

  closeEvaluation(evaluationId: number): Observable<void> {
    const userId = this.authService.currentUser!!.id;

    const url = `${this.apiUrl}closeEvaluation/evaluation/${evaluationId}/${userId}`;
    return this.http.put<void>(url, null);
  }

  sendQuestionnaireAgain(evaluationId: number): Observable<void> {
    const url = `${this.apiUrl}sendQuestionnaireAgain/evaluation/${evaluationId}`;
    return this.http.put<void>(url, null);
  }
  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}delete/${id}`;
    return this.http.delete<void>(url);
  }

  findAll(): Observable<Questionnaire[]> {
    return this.http.get<Questionnaire[]>(this.apiUrl);
  }

  findById(id: number): Observable<Questionnaire> {
    const url = `${this.apiUrl}${id}`;
    return this.http.get<Questionnaire>(url);
  }

  findByEvaluationId(id: number): Observable<Questionnaire> {
    const url = `${this.apiUrl}evaluation/${id}`;
    return this.http.get<Questionnaire>(url);
  }

  findByDnetId(id: string): Observable<Questionnaire[]> {
    const url = `${this.apiUrl}findQuestionnaire/dnetId/${id}`;
    return this.http.get<Questionnaire[]>(url);
  }

  findByUserId(id: number): Observable<Questionnaire[]> {
    const url = `${this.apiUrl}userId/${id}`;
    return this.http.get<Questionnaire[]>(url);
  }

  statusToEnObservacion(id: number): Observable<void> {
    const userId = this.authService.currentUser!!.id;

    const url = `${this.apiUrl}statusToEnObservacion/${id}/${userId}`;
    return this.http.put<void>(url, null);
  }



  downloadAnswer(idType: number, startDate: Date, endDate: Date, language: string, format: string): Observable<Blob> {
    const url = `${this.apiUrl}export`;

    // Formatea las fechas como cadenas en formato ISO
    const formattedStartDate = this.datePipe.transform(startDate, 'yyyy-MM-dd'); // o cualquier otro formato que desees enviar
    const formattedEndDate = this.datePipe.transform(endDate, 'yyyy-MM-dd');

    if (formattedStartDate !== null && formattedEndDate !== null) {
      // Configura los parámetros de la solicitud
      const params = new HttpParams()
        .set('idType', idType)
        .set('startDate', formattedStartDate)
        .set('endDate', formattedEndDate)
        .set('language', language)
        .set('format', format);

      // Realiza la solicitud HTTP con responseType 'blob'
      return this.http.get(url, {
        params: params,
        responseType: 'blob'
      });

    } else {
      console.error('Las fechas formateadas son nulas.');
      return EMPTY;
    }
  }

  downloadAnswerFilter(idType: number, year: number, nombre: string, language: string, format: string): Observable<Blob> {
    const url = `${this.apiUrl}exportFilter`;

    if (year !== null) {
      // Configura los parámetros de la solicitud
      const params = new HttpParams()
        .set('idType', idType)
        .set('year', year)
        .set('nombre', nombre)
        .set('language', language)
        .set('format', format);

      // Realiza la solicitud HTTP con responseType 'blob'
      return this.http.get(url, {
        params: params,
        responseType: 'blob'
      });

    } else {
      console.error('Las fechas formateadas son nulas.');
      return EMPTY;
    }
  }

  downloadCertificatesByYear(idType: number, year: Date) {
    const currentMonth = year.getMonth();

    // Sumar un mes solo si el mes actual es enero
    if (currentMonth === 0) { // Enero
      year.setMonth(currentMonth + 1);
    }

    const url = `${this.apiUrl}downloadCertificate`;
    // Formatea las fechas como cadenas en formato ISO
    const formattedDate = this.datePipe.transform(year, 'yyyy-MM-dd');
    if (formattedDate !== null) {
      // Configura los parámetros de la solicitud
      const params = new HttpParams().set('idType', idType).set('date', formattedDate);

      return this.http.get(url, {
        params: params
      });
    } else {
      console.error('Las fechas formateadas son nulas.');
      return EMPTY;
    }
  }

  getCertificatePDF(id: number) {
    const url = `${this.apiUrl}certificate/${id}`;
    return this.http.get(url, {
      responseType: 'blob'
    });
  }



  downloadReport(evaluation: any, withCloseEvaluation: boolean): Observable<any> {
    // Generar el SVG con Vega y agregarlo al contenedor
    // vegaAraña.width = 200;
    // vegaAraña.height = 280;
    // vegaAraña.padding = 0;
    let vegaChart: any;
    if (this.cacheService.getLanguage() === 'es') {
      vegaChart = new vega.View(vega.parse(vegaArañaPDF)).renderer('svg');
    } else {
      vegaChart = new vega.View(vega.parse(vegaArañaPDFEN)).renderer('svg');
    }

    let chartData = [];
    // const idType = (this.cacheService.retrieveModuleFromStorage() === 'journal') ? 1 : 2;
    const idType = this.cacheService.selectedTypeId;
    if (idType !== null) {
      // Retornar el Observable generado por la solicitud HTTP
      return this.statsService.getStatsByEvaluationId(idType, evaluation.id).pipe(
        // Manipular los datos antes de pasarlos al siguiente operador
        map((data) => {
          chartData = data.map(item => {
            let type = item.type;
            if (this.cacheService.getLanguage() === 'es') {
              // Si el idioma es español, cambia el tipo según la condición
              if (type === 'Advanced') {
                type = 'Avanzado';
              } else if (type === 'Basic') {
                type = 'Básico'
              }
            }
            return {
              category: item.categoria,
              value: item.puntuacion.toFixed(2),
              type: type
            };
          });
          // Devolver los datos para el siguiente operador
          return chartData;
        }),
        // Manejar errores en la solicitud HTTP
        catchError((error: any) => {
          console.error('Error en la solicitud HTTP:', error);
          // Devolver un array vacío en caso de error
          return of([]);
        }),
        // Ejecutar operaciones después de recibir los datos
        tap((chartData) => {
          // Ahora puedes usar chartData en tu gráfico Vega
          vegaChart.data('table', chartData);
          console.dir('chartData', chartData);
          vegaChart.run();
          vegaChart.toSVG().then((result: any) => {
            // Llamar a la función para generar el PDF después de obtener el SVG y agregarlo al DOM
            this.generatePDF(result, evaluation, withCloseEvaluation);
          })
            .catch((error: any) => {
              console.error('Error al generar SVG:', error);
            });
        })
      );
    } else {
      // Si idType es null, retornar un array vacío
      return of([]);
    }
  }


  // Función para generar el PDF

/*
  generatePDF(svg: any, evaluation: any, withCloseEvaluation: boolean) {
    // Crear un nuevo documento PDF
    const pdf = new jsPDF('p', 'pt', 'a4');

    // Establecer márgenes izquierdo y derecho en puntos (por ejemplo, 50 puntos)
    const marginLeft = 50;
    let marginLeftVariable = 50
    const textWidth = 500;

    const logoUrl = 'assets/icon/cabecera.png'; // Ruta de la imagen desde la carpeta assets
    const nombreCuestionario = evaluation.userRepository.repository.institucion.nombre; // Reemplaza con el valor real
    const porcentaje = evaluation.evaluationGrade + '%'; // Reemplaza con el valor real
    const fechaSistema = new Date();
    const fechaFormateada = this.datePipe.transform(fechaSistema, 'dd \'of\' MMMM\',\' yyyy');

    const lang = (this.cacheService.getLanguage() === 'en') ? "en" : "es";

    const dia = fechaSistema.getDate();
    const mes = fechaSistema.toLocaleString(lang, { month: 'long' }); // Nombre del mes
    const anio = fechaSistema.getFullYear();

    var img = new Image();
    img.src = logoUrl;
    pdf.addImage(img, 'JPEG', marginLeft, 10, 190, 80, undefined, 'FAST');

    pdf.setTextColor(43, 43, 94);
    pdf.setFontSize(26);
    pdf.setFont('Helvetica', 'bold');
    let initialTextHeight = 140;

    if (evaluation.questionnaireType.id == 1) pdf.text(this.translate.instant('PDF_CERTIFICATE_EQSIP'), marginLeft, initialTextHeight);
    else pdf.text(this.translate.instant('PDF_CERTIFICATE_EQSIP'), marginLeft, initialTextHeight);

    pdf.setTextColor(0, 0, 0);
    pdf.setFontSize(12);

    var xPosition = marginLeft;
    var yPosition = initialTextHeight;

    pdf.setFont('Helvetica', 'normal');
    yPosition = initialTextHeight + 25;
    var texto1 = this.translate.instant('PDF_SUCCESS')
    var widthTexto1 = pdf.getStringUnitWidth(texto1) * 12;
    pdf.splitTextToSize(texto1, textWidth)
    pdf.text(texto1, xPosition, yPosition);

    pdf.setFont('Helvetica', 'bold');
    xPosition += widthTexto1
    var text2 = pdf.splitTextToSize(nombreCuestionario , textWidth)
    var widthTexto2 = pdf.getStringUnitWidth(nombreCuestionario) * 12;
    pdf.text(text2, xPosition, yPosition);

    pdf.setFont('Helvetica', 'normal');
    xPosition += widthTexto1 + widthTexto2
    var dateStr = pdf.splitTextToSize(this.translate.instant('PDF_DATE') + porcentaje + this.translate.instant('PDF_PARAP') , textWidth)
    pdf.text(dateStr, xPosition, yPosition);

    var optionalApostrofe = (this.cacheService.getLanguage() === 'en') ? "'s" : "";

    initialTextHeight = initialTextHeight + 55;
    var percentageStr = pdf.splitTextToSize(this.translate.instant('PDF_PERCENTAGE') + nombreCuestionario + optionalApostrofe + this.translate.instant('PDF_NAME_QUESTIONNAIRE'), textWidth)
    pdf.text(percentageStr, marginLeft, initialTextHeight);

    initialTextHeight = initialTextHeight + 45;
    var recognitionStr = pdf.splitTextToSize(this.translate.instant('PDF_RECOGNITION') + nombreCuestionario + optionalApostrofe + this.translate.instant('PDF_PARAP3'), textWidth)
    pdf.text(recognitionStr, marginLeft, initialTextHeight);

    const fechaFormateada2 = `${dia} ${this.translate.instant('ANTIPOSICION')} ${mes}, ${anio}`;

    pdf.setFont('Helvetica', 'bold');
    initialTextHeight = initialTextHeight + 65;
    pdf.text(fechaFormateada2, marginLeft, initialTextHeight);

    pdf.setFont('Helvetica', 'normal');
    initialTextHeight = initialTextHeight + 45;
    var dateStr = pdf.splitTextToSize(this.translate.instant('PDF_SIGN') , textWidth)
    pdf.text(dateStr, marginLeft, initialTextHeight);

    // Ajustar las dimensiones del PDF y agregar la imagen
    this.promiseSVG(pdf, svg).then(() => {
      setTimeout(() => {
        // Crea un formulario de datos para enviar el blob como parte de la solicitud POST
        // const formData = new FormData();
        // formData.append('pdfBlob', pdf.output('blob'));

        // Obtener el Blob directamente
        var pdfBlob = pdf.output('blob');

        // Crear un formulario para enviar el Blob como parte de la solicitud
        var formData = new FormData();
        formData.append('pdfFile', pdfBlob, `${evaluation.userRepository.repository.institucion.acronimo}_${evaluation.userRepository.user.nombre}_${evaluation.userRepository.user.apellidos}_${this.translate.instant('CERTIFICATE')}`);

        if (withCloseEvaluation) {
          const userId = this.authService.currentUser!!.id?.toString();
          if (userId) {
            formData.set('id', evaluation.id.toString());
            formData.set('actionAuthor', userId);
            this.http.post(this.apiUrl + 'closeevaluation-pdf', formData, { responseType: 'blob' })
              .subscribe(
                response => {
                  // Guardar el blob localmente utilizando file-saver

                  this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_CONTENT_CUESTIONARIO"))
                  this.cacheService.getLanguage();
                  if (this.cacheService.getLanguage() === 'en') {
                    saveAs(response, 'report.zip');
                  } else {
                    saveAs(response, 'report.zip');
                  }
                  this.router.navigate(['/home-editor']);
                },
                error => {
                }
              );
          }
        } else {
          // Realizar la solicitud POST
          this.http.post(this.apiUrl + 'process-pdf', formData, { responseType: 'blob' })
            .subscribe(
              response => {
                // Guardar el blob localmente utilizando file-saver
                if (this.cacheService.getLanguage() === 'en') {
                  saveAs(response, 'report.zip');
                } else {
                  saveAs(response, 'report.zip');
                }
              },
              error => {
              }
            );
        }
      }, 500);
    });
  }
*/

  async generatePDF(svg: string, evaluation: any, withCloseEvaluation: boolean) {
    const logoUrl = 'assets/icon/cabecera.png';
    const nombreCuestionario = evaluation.userRepository.repository.institucion.nombre;
    const porcentaje = evaluation.evaluationGrade + '%';
    const fechaSistema = new Date();
    const fechaFormateada = this.datePipe.transform(fechaSistema, 'dd \'of\' MMMM\',\' yyyy');
    var optionalApostrofe = (this.cacheService.getLanguage() === 'en') ? "'s" : "";
    const lang = (this.cacheService.getLanguage() === 'en') ? "en" : "es";
    const dia = fechaSistema.getDate();
    const mes = fechaSistema.toLocaleString(lang, { month: 'long' });
    const anio = fechaSistema.getFullYear();
    const fechaFormateada2 = `${dia} ${this.translate.instant('ANTIPOSICION')} ${mes}, ${anio}`;
    const titulo = evaluation.questionnaireType.id == 1 ? this.translate.instant('PDF_CERTIFICATE_EQSIP') : this.translate.instant('PDF_CERTIFICATE_EQSIP');

    const pdfWidth = 793;
    const pdfHeight = 1122;

    const pdf = new jsPDF({
      orientation: 'portrait',
      unit: 'px',
      format: [pdfWidth, pdfHeight]
    });

    const htmlPath = '../../../assets/html/certificado.html';

    fetch(htmlPath)
      .then(response => response.text())
      .then(async (html) => {
        html = html.replace('{TITULO}', titulo);
        html = html.replace('{TEXTO1}', this.translate.instant(this.translate.instant('PDF_SUCCESS')));
        html = html.replace('{NOMBRE}', nombreCuestionario);
        html = html.replace('{TEXTO2}', this.translate.instant('PDF_DATE'));
        html = html.replace('{PORCENTAJE}', porcentaje);
        html = html.replace('{TEXTO3}', this.translate.instant('PDF_PARAP'));
        html = html.replace('{TEXTO4}', this.translate.instant('PDF_PERCENTAGE'));
        html = html.replaceAll('{NOMBRESPECIAL}', nombreCuestionario + optionalApostrofe);
        html = html.replace('{TEXTO5}', this.translate.instant('PDF_NAME_QUESTIONNAIRE'));
        html = html.replace('{TEXTO6}', this.translate.instant('PDF_RECOGNITION'));
        html = html.replace('{TEXTO7}', this.translate.instant('PDF_PARAP3'));
        html = html.replace('{FECHA}', fechaFormateada2);
        html = html.replace('{TEXTO8}', this.translate.instant('PDF_SIGN'));

        pdf.addImage(logoUrl, 'PNG', 60, 40, 200, 100);

        pdf.html(html, {
          callback: async (pdf) => {
            const svgWidth = 500;
            const svgHeight = 400;

            // Convertir el SVG a PNG
            const pngDataUrl = await this.convertSvgToPng(svg, svgWidth, svgHeight);

            // Calcular la posición X para centrar la imagen horizontalmente
            const x = (pdf.internal.pageSize.getWidth() - svgWidth) / 2;

            // Añadir el PNG al PDF
            pdf.addImage(pngDataUrl, 'PNG', x, pdf.internal.pageSize.getHeight() - 450, svgWidth, svgHeight);
            pdf.deletePage(2);


            // Generar un archivo ZIP
            const zip = new JSZip();
            const nombrefile = `${evaluation.userRepository.repository.institucion.acronimo}_${evaluation.userRepository.user.nombre}_${evaluation.userRepository.user.apellidos}_report`;

            // Añadir el PDF al ZIP
            zip.file(`${nombrefile}.pdf`, pdf.output('blob'));

            // Generar PNG de la primera página del PDF
            const pdfData = await pdf.output('blob');

            var formData = new FormData();
            formData.append('pdfFile', pdfData, `${evaluation.userRepository.repository.institucion.acronimo}_${evaluation.userRepository.user.nombre}_${evaluation.userRepository.user.apellidos}_report`);


            const pdfFirstPageBlob = await this.getFirstPageBlob(pdfData);
/*
            this.http.post(this.apiUrl + 'process-pdf', formData, { responseType: 'blob' })
              .subscribe(
                response => {
                  // Guardar el blob localmente utilizando file-saver
                  if (this.cacheService.getLanguage() === 'en') {
                    saveAs(response, 'report.zip');
                  } else {
                    saveAs(response, 'report.zip');
                  }
                },
                error => {
                }
              );*/

            if (withCloseEvaluation) {
              const userId = this.authService.currentUser!!.id?.toString();
              if (userId) {
                formData.set('id', evaluation.id.toString());
                formData.set('actionAuthor', userId);
                this.http.post(this.apiUrl + 'closeevaluation-pdf', formData, { responseType: 'blob' })
                  .subscribe(
                    response => {
                      // Guardar el blob localmente utilizando file-saver

                      this.utilService.handleSuccess(this.getExportTranslation("SUCCESS_MODAL_CONTENT_CUESTIONARIO"))
                      this.cacheService.getLanguage();
                      if (this.cacheService.getLanguage() === 'en') {
                        saveAs(response, 'report.zip');
                      } else {
                        saveAs(response, 'report.zip');
                      }
                      this.router.navigate(['/home-editor']);
                    },
                    error => {
                    }
                  );
              }
            } else {
              // Realizar la solicitud POST
              this.http.post(this.apiUrl + 'process-pdf', formData, { responseType: 'blob' })
                .subscribe(
                  response => {
                    // Guardar el blob localmente utilizando file-saver
                    if (this.cacheService.getLanguage() === 'en') {
                      saveAs(response, 'report.zip');
                    } else {
                      saveAs(response, 'report.zip');
                    }
                  },
                  error => {
                  }
                );
            }

            /*
                      // Realizar la solicitud POST
          this.http.post(this.apiUrl + 'process-pdf', formData, { responseType: 'blob' })
            .subscribe(
              response => {
                // Guardar el blob localmente utilizando file-saver
                if (this.cacheService.getLanguage() === 'en') {
                  saveAs(response, 'report.zip');
                } else {
                  saveAs(response, 'report.zip');
                }
              },
              error => {
              }
            );

             */

            // Añadir el PNG al ZIP
/*            zip.file(`${nombrefile}.png`, pdfFirstPageBlob);

            // Generar el archivo ZIP
            zip.generateAsync({ type: 'blob' })
              .then((content) => {
                // Crear un objeto URL para el archivo ZIP
                const zipUrl = URL.createObjectURL(content);

                // Crear un enlace para descargar el archivo ZIP
                const link = document.createElement('a');
                link.href = zipUrl;
                link.download = 'report.zip'; // Nombre del archivo ZIP
                link.click();

                // Limpiar el objeto URL después de la descarga
                URL.revokeObjectURL(zipUrl);
              })
              .catch(error => console.error('Error al generar el archivo ZIP:', error));*/
          }
        });
      })
      .catch(error => console.error('Error al generar el PDF:', error));
  }

  async getFirstPageBlob(pdfBlob: Blob): Promise<Blob> {
    return new Promise<Blob>((resolve, reject) => {
      const fileReader = new FileReader();
      fileReader.onload = () => {
        const arrayBuffer = fileReader.result as ArrayBuffer;
        const uint8Array = new Uint8Array(arrayBuffer);

        // Encontrar el índice donde comienza la siguiente página
        let pageIndex = 0;
        for (let i = 0; i < uint8Array.length - 4; i++) {
          if (
            uint8Array[i] === 0x25 &&  // '%'
            uint8Array[i + 1] === 0x50 &&  // 'P'
            uint8Array[i + 2] === 0x44 &&  // 'D'
            uint8Array[i + 3] === 0x46     // 'F'
          ) {
            pageIndex = i;
            break;
          }
        }

        // Obtener los bytes de la primera página
        const firstPageUint8Array = uint8Array.slice(0, pageIndex);

        // Crear un nuevo Blob con los bytes de la primera página
        const firstPageBlob = new Blob([firstPageUint8Array], { type: 'application/pdf' });
        resolve(firstPageBlob);
      };
      fileReader.onerror = reject;
      fileReader.readAsArrayBuffer(pdfBlob);
    });
  }



  convertSvgToPng(svg: string, width: number, height: number): Promise<string> {
    return new Promise((resolve, reject) => {
      // Crear un elemento canvas
      const canvas = document.createElement('canvas');
      canvas.width = width;
      canvas.height = height;
      const ctx = canvas.getContext('2d');

      // Crear una imagen y asignarle el SVG convertido a Data URL
      const img = new Image();
      img.onload = () => {
        // Dibujar la imagen (SVG) en el canvas
        // @ts-ignore
        ctx.drawImage(img, 0, 0, width, height);
        // Convertir el contenido del canvas a PNG
        const pngDataUrl = canvas.toDataURL('image/png');
        resolve(pngDataUrl);
      };
      img.onerror = (e) => reject(e);

      // Convertir el SVG a Data URL y cargarlo en la imagen
      const svgBlob = new Blob([svg], {type: 'image/svg+xml;charset=utf-8'});
      const url = URL.createObjectURL(svgBlob);
      img.src = url;
    });
  }


  promiseSVG(pdf: jsPDF, svg: string): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      pdf.addSvgAsImage(svg, 100, 400, 400, 430);
      resolve(); // Resuelve la promesa cuando el código anterior se completa
    });
  }

  async downloadReports(cuestionario: any): Promise<Blob> {
    try {
      // Generar el SVG con Vega y agregarlo al contenedor
      // vegaAraña.width = 200;
      // vegaAraña.height = 280;
      // vegaAraña.padding = 0;
      let vegaChart;
      if (this.cacheService.getLanguage() === 'es') {
        vegaChart = new vega.View(vega.parse(vegaArañaPDF)).renderer('svg');
      } else {
        vegaChart = new vega.View(vega.parse(vegaArañaPDFEN)).renderer('svg');
      }

      let chartData = [];
      const idType = this.cacheService.selectedTypeId;

      if (idType !== null) {
        const data = await this.statsService.getStatsDividedByQuestionnaire(idType, cuestionario.id).toPromise();

        if (data) {
          chartData = data.map(item => {
            let type = item.type;
            if (this.cacheService.getLanguage() === 'es') {
              // Si el idioma es español, cambia el tipo según la condición
              if (type === 'Advanced') {
                type = 'Avanzado';
              } else if (type === 'Basic') {
                type = 'Básico'
              }
            }
            return {
              category: item.categoria,
              value: item.puntuacion.toFixed(2),
              type: type
            };
          });

          // Ahora puedes usar chartData en tu gráfico Vega
          vegaChart.data('table', chartData);
          vegaChart.run();

          console.dir('vegaChartdata', chartData);

          const result = await vegaChart.toSVG();

          // Llamar a la función para generar el PDF después de obtener el SVG y agregarlo al DOM
          const pdfBlob = await this.generatePDFs(result, cuestionario.evaluation);

          return pdfBlob;  // Devolver el Blob directamente
        }
      }

      // Manejar el caso en que no hay datos o algo salió mal
      throw new Error('No se pudieron obtener datos para generar el PDF');
    } catch (error) {
      console.error('Error al generar el PDF:', error);
      // Puedes manejar el error de alguna manera si es necesario
      throw error;
    }
  }

  // Función para generar el PDF
  generatePDFs(svg: any, evaluation: any): Promise<Blob> {
    return new Promise<Blob>((resolve, reject) => {
      // Crear un nuevo documento PDF
      const pdf = new jsPDF('p', 'pt', 'a4');

      // Establecer márgenes izquierdo y derecho en puntos (por ejemplo, 50 puntos)
      const marginLeft = 50;
      const textWidth = 500;

      const logoUrl = 'assets/icon/cabecera.png'; // Ruta de la imagen desde la carpeta assets
      const nombreCuestionario = evaluation.userRepository.repository.institucion.nombre; // Reemplaza con el valor real
      const porcentaje = evaluation.evaluationGrade + '%'; // Reemplaza con el valor real
      const fechaSistema = new Date();
      let fechaFormateada;
      if (this.cacheService.getLanguage() === 'es') {
        fechaFormateada = this.datePipe.transform(fechaSistema, 'dd \'de\' MMMM \'de\' yyyy', undefined, 'es');
      } else {
        fechaFormateada = this.datePipe.transform(fechaSistema, 'dd \'of\' MMMM\',\' yyyy');
      }

      var img = new Image();
      img.src = logoUrl;
      pdf.addImage(img, 'JPEG', marginLeft, 10, 190, 80, undefined, 'FAST');

      pdf.setTextColor(43, 43, 94);
      pdf.setFontSize(26);
      pdf.setFont('Helvetica', 'bold');
      let initialTextHeight = 140;
      pdf.text(this.translate.instant('PDF_CERTIFICATE'), marginLeft, initialTextHeight);

      pdf.setTextColor(0, 0, 0);
      pdf.setFontSize(12);
      pdf.setFont('Helvetica', 'normal')
      initialTextHeight = initialTextHeight + 35;
      pdf.text(this.translate.instant('PDF_CERTIFIES'), marginLeft, initialTextHeight);

      pdf.setFont('Helvetica', 'bold');
      initialTextHeight = initialTextHeight + 25;
      pdf.text(nombreCuestionario, marginLeft, initialTextHeight);

      pdf.setFont('Helvetica', 'normal');
      initialTextHeight = initialTextHeight + 25;
      var dateStr = pdf.splitTextToSize(this.translate.instant('PDF_SUCCESS') + porcentaje + this.translate.instant('PDF_DATE') + fechaFormateada, textWidth)
      pdf.text(dateStr, marginLeft, initialTextHeight);

      initialTextHeight = initialTextHeight + 45;
      var percentageStr = pdf.splitTextToSize(this.translate.instant('PDF_PERCENTAGE') + nombreCuestionario + this.translate.instant('PDF_NAME_QUESTIONNAIRE'), textWidth)
      pdf.text(percentageStr, marginLeft, initialTextHeight);

      initialTextHeight = initialTextHeight + 45;
      var recognitionStr = pdf.splitTextToSize(this.translate.instant('PDF_RECOGNITION'), textWidth)
      pdf.text(recognitionStr, marginLeft, initialTextHeight);

      initialTextHeight = initialTextHeight + 45;
      var givenStr = pdf.splitTextToSize(this.translate.instant('PDF_GIVEN') + fechaFormateada, textWidth)
      pdf.text(givenStr, marginLeft, initialTextHeight);

      pdf.setFont('Helvetica', 'bold');
      initialTextHeight = initialTextHeight + 35;
      pdf.text('DIAMAS', marginLeft, initialTextHeight);

      // Ajustar las dimensiones del PDF y agregar la imagen
      // pdf.addImage(canvas.toDataURL('image/png', 1.0), 'JPEG', marginLeft, 465, 500, 380);

      this.promiseSVG(pdf, svg).then(() => {
        setTimeout(() => {
          // Obtener el contenido del PDF como un array de bytes
          const pdfBytes = pdf.output('blob');

          // Resolver la promesa con el array de bytes del PDF
          resolve(pdfBytes);
        }, 500);
      });

    });
  }

  async generateAndSendPDFs(formData: FormData): Promise<void> {
    try {
      // Realizar la solicitud POST para enviar los Blobs al backend
      await this.http.post(this.apiUrl + 'process-multiple-pdfs', formData, { responseType: 'blob' }).toPromise()
        .then(response => {
          // Guardar el blob localmente utilizando file-saver
          if (response instanceof Blob) {
            if (this.cacheService.getLanguage() === 'en') {
              saveAs(response, 'certificates.zip');
            } else {
              saveAs(response, 'certificados.zip');
            }
          }
        })
        .catch(error => {
          console.error('Error al descargar el ZIP', error);
        });

      console.log('Todos los PDFs fueron enviados exitosamente al backend');
    } catch (error) {
      console.error('Error al enviar los PDFs al backend:', error);
      // Puedes manejar el error de alguna manera si es necesario
    }
  }

  getExportTranslation(key: string): string {
    const currentLanguage = this.cacheService.getLanguage()
    // @ts-ignore
    const translations = exportTranslations[currentLanguage];
    return translations[key] || '';
  }

}

