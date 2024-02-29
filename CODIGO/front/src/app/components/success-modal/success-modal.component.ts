import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-success-modal',
  template: `
    <div class="header">
      <h2 mat-dialog-title>{{ data.title }}</h2>
      <mat-icon class="close-icon" (click)="onClose()">close</mat-icon>
    </div>
    <mat-dialog-content [innerHTML]="data.content">{{ data.content }}</mat-dialog-content>
    <mat-dialog-actions>
      <button class="btn btn-primary" (click)="onClose()">{{"CERRAR" | translate}}</button>
    </mat-dialog-actions>
  `,
  styles: [`
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px;
      border-bottom:1px solid silver;
    }
    .close-icon {
      cursor: pointer;
    }

  `]
})
export class SuccessModalComponent {
  [x: string]: any;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
  private dialogRef: MatDialogRef<SuccessModalComponent>) {}

  onClose() {
    this.dialogRef?.close();  // Cierra el modal al pulsar "Cerrar"
  }
}
