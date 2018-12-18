import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FinisherRoutingModule } from './finisher-routing.module';
import { FinisherComponent } from './pages/finisher/finisher.component';
import { ObservationFinishComponent } from './pages/observation-finish/observation-finish.component';

import { MaterialCommonModule } from '../material-common/material-common.module'
import { ProcessService } from '../../core/services/process.service';
import { IconsService } from '../../core/services/icons.service';
import { ResponseServerService } from '../../core/services/response-server.service';

@NgModule({
  imports: [
    CommonModule,
    FinisherRoutingModule,
    MaterialCommonModule
  ],
  declarations:[
  		FinisherComponent, 
  		ObservationFinishComponent
  	],
  providers: [
	  	ProcessService,
	  	IconsService,
      	ResponseServerService
  	]
})
export class FinisherModule { }
