import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './pages/home/home.component';
import { AuthenticationService } from '../../core/authentication/authentication.service';
import { TokenStorage } from '../../core/storage/token.storage';
import { RolesStorage } from '../../core/storage/roles.storage';
import { MaterialCommonModule } from '../material-common/material-common.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    HomeRoutingModule,
    MaterialCommonModule,
    SharedModule
  ],
  declarations: [
  	HomeComponent
    ],
  providers: [
    AuthenticationService,
    TokenStorage,
    RolesStorage
    ]
})
export class HomeModule { }
