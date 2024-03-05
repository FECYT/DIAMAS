import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { CaptchaComponent } from './captcha/captcha.component';
import { FormsModule } from '@angular/forms';
import { RecaptchaModule } from 'ng-recaptcha';
import { RecaptchaFormsModule } from 'ng-recaptcha';
import { RecaptchaComponent } from './recaptcha/recaptcha.component';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field'; // Importa MatFormFieldModule
import { MatSelectModule } from '@angular/material/select'; // Importa MatSelectModule
import { GestionComponent } from './components/gestion/gestion.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { AgregarTerminoComponent } from './components/agregar-termino/agregar-termino.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdministrarCategoriasComponent } from './components/administrar-categorias/administrar-categorias.component';
import { NuevoPeriodoComponent } from './components/nuevo-periodo/nuevo-periodo.component';
import { NuevaPreguntaComponent } from './components/nueva-pregunta/nueva-pregunta.component';
import { AgregarVocabularioComponent } from './components/agregar-vocabulario/agregar-vocabulario.component';
import { AdministrarPeriodosComponent } from './components/administrar-periodos/administrar-periodos.component';
import { SuccessModalComponent } from './components/success-modal/success-modal.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ListaTerminosComponent } from './components/lista-terminos/lista-terminos.component';
import { HistorialAccionesComponent } from './components/historial-acciones/historial-acciones.component';
import { HomeEditorComponent } from './components/home-editor/home-editor.component';
import { CuestionarioComponent } from './components/cuestionario/cuestionario.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AccesoDenegadoComponent } from './components/acceso-denegado/acceso-denegado.component';
import { ExportarRespuestasComponent } from './components/modals/exportar-respuestas/exportar-respuestas.component';
import { ExportarDocumentosComponent } from './components/modals/exportar-documentos/exportar-documentos.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { AuthInterceptor } from './services/interceptor/auth.interceptor';
import { MatSortModule } from '@angular/material/sort';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { FilterPipe } from './services/general/filter.pipe';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { QuestionModalComponent } from './components/question-modal/question-modal.component';
import { SwitchComponent } from './shared/switch/switch.component';
import { DatePipe, registerLocaleData } from '@angular/common';
import { JWT_OPTIONS, JwtHelperService, JwtModule } from '@auth0/angular-jwt';
import { DescargarCertificadosComponent } from './components/modals/descargar-certificados/descargar-certificados.component';
import { MessageService } from "primeng/api";
import { ToastModule } from "primeng/toast";
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatChipsModule } from '@angular/material/chips';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatPaginatorIntlEspañol } from './translation/es-MatPaginatorIntl';
import { VegaChartComponent } from "./components/vega-chart/vega-chart.component";
import { NgxVegaModule } from "ngx-vega";
import { CalendarModule } from "primeng/calendar";
import { PrimeNGConfig } from 'primeng/api';
import { EstadisticasSeleccionComponent } from './components/estadisticas-seleccion/estadisticas-seleccion.component';
import { TableModule } from "primeng/table";
import { UserGestionComponent } from './components/user-gestion/user-gestion.component';
import { MultiSelectModule } from "primeng/multiselect";
import { RecoverPasswordComponent } from './components/recover-password/recover-password.component';
import { SearchEditorComponent } from './components/search-editor/search-editor.component';
import { MatSelectCountryModule } from "@angular-material-extensions/select-country";
import { RadioButtonModule } from "primeng/radiobutton";
import { CheckboxModule } from "primeng/checkbox";
import { HerramientaComparativaComponent } from './components/herramienta-comparativa/herramienta-comparativa.component';
import { DropdownModule } from "primeng/dropdown";
import { ModuleSelectorComponent } from './components/module-selector/module-selector.component';
import { ModuleDropdownComponent } from './components/module-dropdown/module-dropdown.component';
import localeEs from '@angular/common/locales/es';
import {ListaEvaluacionesComponent} from "./components/lista-evaluaciones/lista-evaluaciones.component";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function jwtOptionsFactory() {
  return {
    tokenGetter: () => {
      return localStorage.getItem('access_token');
    },
    allowedDomains: ['example.com'],
    disallowedRoutes: ['http://example.com/api/auth']
  };
}


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    InicioComponent,
    CaptchaComponent,
    RecaptchaComponent,
    ListaEvaluacionesComponent,
    GestionComponent,
    UsuarioComponent,
    AgregarTerminoComponent,
    AdministrarCategoriasComponent,
    NuevoPeriodoComponent,
    NuevaPreguntaComponent,
    AgregarVocabularioComponent,
    AdministrarPeriodosComponent,
    SuccessModalComponent,
    ListaTerminosComponent,
    HistorialAccionesComponent,
    HomeEditorComponent,
    CuestionarioComponent,
    AccesoDenegadoComponent,
    ExportarRespuestasComponent,
    ExportarDocumentosComponent,
    FilterPipe,
    QuestionModalComponent,
    SwitchComponent,
    DescargarCertificadosComponent,
    VegaChartComponent,
    EstadisticasSeleccionComponent,
    UserGestionComponent,
    RecoverPasswordComponent,
    SearchEditorComponent,
    HerramientaComparativaComponent,
    ModuleSelectorComponent,
    ModuleDropdownComponent
  ],
  imports: [
    JwtModule.forRoot({
      jwtOptionsProvider: {
        provide: JWT_OPTIONS,
        useFactory: jwtOptionsFactory
      }
    }),
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    RecaptchaModule,
    RecaptchaFormsModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatInputModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatMenuModule,
    MatButtonModule,
    MatDialogModule,
    MatTooltipModule,
    MatDatepickerModule,
    MatCheckboxModule,
    MatNativeDateModule,
    MatSortModule,
    MatMomentDateModule,
    MatProgressSpinnerModule,
    ToastModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatPaginatorModule,
    CalendarModule,
    TableModule,
    MultiSelectModule,
    MatSelectCountryModule.forRoot('en'),
    RadioButtonModule,
    CheckboxModule,
    DropdownModule
  ],
  providers: [
    MessageService,
    JwtHelperService,
    { provide: MatPaginatorIntl, useClass: MatPaginatorIntlEspañol },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
    registerLocaleData(localeEs); // Registrar los datos de localización para español
  }
}
