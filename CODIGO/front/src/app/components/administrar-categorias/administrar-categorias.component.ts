import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrar-categorias',
  templateUrl: './administrar-categorias.component.html',
  styleUrls: ['./administrar-categorias.component.scss']
})
export class AdministrarCategoriasComponent {
  displayedColumns: string[] = ['nombre', 'descripcion', 'acciones'];
  dataSource = new MatTableDataSource([
    { nombre: 'Vocabulario periodo', descripcion: 'Lorem ipsum dolor' },
    { nombre: 'Vocabulario periodo especial', descripcion: 'Lorem ipsum dolor' }
  ]);

  constructor(private router: Router) {}

  agregarVocabulario(): void {
    this.router.navigate(['/agregar-vocabulario']);
  }
  agregarTermino(): void {
    this.router.navigate(['/agregarTermino']);
  }
}
