import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatMenuModule } from '@angular/material/menu';
import { HeaderComponent } from '../../core/header/header.component';
import { FooterComponent } from '../../core/footer/footer.component';
import { MaterialCommonModule } from '../material-common/material-common.module';
import { SidebarComponent } from '../../core/sidebar/sidebar.component';
import { RolesStorage } from '../../core/storage/roles.storage';

@NgModule({
  imports: [
    RouterModule,
    CommonModule,
    MatMenuModule,
    MaterialCommonModule
  ],
  declarations: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent
    ],
  exports: [
        RouterModule,
        HeaderComponent,
        FooterComponent,
        SidebarComponent
    ],
  providers: [
    RolesStorage
  ]
})
export class SharedModule { }
