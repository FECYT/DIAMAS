import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { GestionComponent } from './components/gestion/gestion.component';
import { AgregarTerminoComponent } from './components/agregar-termino/agregar-termino.component';
import { AdministrarCategoriasComponent } from './components/administrar-categorias/administrar-categorias.component';
import { NuevoPeriodoComponent } from './components/nuevo-periodo/nuevo-periodo.component';
import { NuevaPreguntaComponent } from './components/nueva-pregunta/nueva-pregunta.component';
import { AgregarVocabularioComponent } from './components/agregar-vocabulario/agregar-vocabulario.component';
import { AdministrarPeriodosComponent } from './components/administrar-periodos/administrar-periodos.component';
import { ListaTerminosComponent } from './components/lista-terminos/lista-terminos.component';
import { HomeEditorComponent } from './components/home-editor/home-editor.component';
import { CuestionarioComponent } from './components/cuestionario/cuestionario.component';
import { AuthGuard } from './services/auth.guard';
import { inject } from '@angular/core';
import { AccesoDenegadoComponent } from './components/acceso-denegado/acceso-denegado.component';
import { ExportarDocumentosComponent } from './components/modals/exportar-documentos/exportar-documentos.component';
import { ExportarRespuestasComponent } from './components/modals/exportar-respuestas/exportar-respuestas.component';
import { DescargarCertificadosComponent } from './components/modals/descargar-certificados/descargar-certificados.component';
import {VegaChartComponent} from "./components/vega-chart/vega-chart.component";
import {EstadisticasSeleccionComponent} from "./components/estadisticas-seleccion/estadisticas-seleccion.component";
import {UserGestionComponent} from "./components/user-gestion/user-gestion.component";
import {RecoverPasswordComponent} from "./components/recover-password/recover-password.component";
import { SearchEditorComponent } from './components/search-editor/search-editor.component';
import {HerramientaComparativaComponent} from "./components/herramienta-comparativa/herramienta-comparativa.component";
import {ModuleSelectorComponent} from "./components/module-selector/module-selector.component";
import {ListaEvaluacionesComponent} from "./components/lista-evaluaciones/lista-evaluaciones.component";



const routes: Routes = [
  { path: '', component: InicioComponent },
  { path: 'login', component: LoginComponent },
  { path: 'listaEvaluacionesActiva', component: ListaEvaluacionesComponent, canActivate: [AuthGuard], data: { roles: ['EVALUADOR', 'SUPER EVALUADOR', 'ADMINISTRADOR', 'EDITOR'] } },
  { path: 'gestion', component: GestionComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'agregarTermino', component: AgregarTerminoComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'agregarTermino/:id', component: AgregarTerminoComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'administrar-categoria', component: AdministrarCategoriasComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'nuevo-periodo', component: NuevoPeriodoComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'nuevo-periodo/:id', component: NuevoPeriodoComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'nueva-pregunta', component: NuevaPreguntaComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'agregar-vocabulario', component: AgregarVocabularioComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'administrar-periodos', component: AdministrarPeriodosComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'lista-terminos', component: ListaTerminosComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'home-editor', component: HomeEditorComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR', 'EDITOR'] } },
  { path: 'exportar-respuestas', component: ExportarRespuestasComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'exportar-documentos', component: ExportarDocumentosComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'descargar-certificados', component: DescargarCertificadosComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'cuestionario', component: CuestionarioComponent, canActivate: [AuthGuard], data: { roles: ['RESPONSABLE REPOSITORIO', 'EVALUADOR', 'ADMINISTRADOR', 'SUPER EVALUADOR', 'EDITOR'] } },
  { path: 'acceso-denegado', component: AccesoDenegadoComponent },
  { path: 'estadisticas', component: VegaChartComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR','EDITOR'] } },
  { path: 'estadisticas/seleccion', component: EstadisticasSeleccionComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR','EDITOR'] } },
  { path: 'user-gestion', component: UserGestionComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'recover-password', component: RecoverPasswordComponent },
  { path: 'herramienta-comparativa', component: HerramientaComparativaComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRADOR'] } },
  { path: 'module-selector', component: ModuleSelectorComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
