import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-historial-acciones',
  templateUrl: './historial-acciones.component.html',
  styleUrls: ['./historial-acciones.component.scss']
})
export class HistorialAccionesComponent {

  evaluators = ""

  constructor(
    public dialogRef: MatDialogRef<HistorialAccionesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onClose(): void {
    this.dialogRef.close();
  }

  ngOnInit(){
  }

}
