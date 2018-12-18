import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { MaterialCommonModule } from '../material-common/material-common.module'
import { ProcessRoutingModule } from './process-routing.module';
import { ProcessComponent } from './pages/process/process.component';
import { CreateProcessComponent } from './pages/create-process/create-process.component';
import { ListProcessComponent } from './pages/list-process/list-process.component';
import { ExecutionService } from '../../core/services/execution.service';
import { UserService } from '../../core/services/user.service';
import { ProcessService } from '../../core/services/process.service';
import { CatalogService } from '../../core/services/catalog.service';
import { DialogsModule } from '../shared/dialogs.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditProcessComponent } from './pages/edit-process/edit-process.component';
import { IconsService } from '../../core/services/icons.service';
import {ResponseServerService} from '../../core/services/response-server.service';
import { ObservationsComponent } from './pages/observations/observations.component';
import { AssignUserComponent } from './pages/assign-user/assign-user.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { StatusService } from '../../core/services/status.service';
import { ObservationService } from '../../core/services/observation.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
  	NgbModule.forRoot(),
    ProcessRoutingModule,
    MaterialCommonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    DialogsModule
  ],
  declarations: [
    ProcessComponent, 
    CreateProcessComponent, 
    ListProcessComponent, 
    EditProcessComponent, 
    ObservationsComponent, 
    AssignUserComponent
  ],
  providers: [
	  	ProcessService,
	  	IconsService,
      CatalogService,
      ResponseServerService,
      ExecutionService,
      UserService,
      StatusService,
      ObservationService
  	]
})
export class ProcessModule { }
