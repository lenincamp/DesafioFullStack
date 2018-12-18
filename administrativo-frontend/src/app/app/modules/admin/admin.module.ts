import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminRoutingModule} from './admin-routing.module';
import {AdminComponent} from './admin/admin.component';
import {SharedModule} from '../shared/shared.module';
import {AuthGuardService} from '../../core/services/auth-guard.service';
import {AuthService} from '../../core/services/auth.service';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule
  ],
  declarations: [AdminComponent],
  providers: [AuthGuardService, AuthService]
})
export class AdminModule { }
