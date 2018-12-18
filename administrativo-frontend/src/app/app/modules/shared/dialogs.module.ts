import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { DeleteConfirmComponent } from '../../core/dialogs/delete-confirm/delete-confirm.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  declarations: [
  	DeleteConfirmComponent
  ],
  exports:[
  	DeleteConfirmComponent
  ]
})
export class DialogsModule { }
