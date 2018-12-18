import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialCommonModule } from '../material-common/material-common.module';
import { MatStepperModule } from '@angular/material/stepper';
import { OrderRoutingModule } from './order-routing.module';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { MainMenuComponent } from './pages/main-menu/main-menu.component';

@NgModule({
  imports: [
    CommonModule,
    OrderRoutingModule,
    MatStepperModule,
    MaterialCommonModule
  ],
  declarations: [
	  WelcomeComponent, 
	  MainMenuComponent
  ]
})
export class OrderModule { }
