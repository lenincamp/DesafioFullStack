import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

import {MaterialCommonModule} from '../material-common/material-common.module';
import {UserRoutingModule} from './user-routing.module';
import {UserComponent} from './pages/user/user.component';
import {CreateUserComponent} from './pages/create-user/create-user.component';
import {RolesStorage} from '../../core/storage/roles.storage';
import {IconsService} from '../../core/services/icons.service';
import {ListUserComponent} from './pages/list-user/list-user.component';
import {UserService} from '../../core/services/user.service';
import {ResponseServerService} from '../../core/services/response-server.service';
import { EditUserComponent } from './pages/edit-user/edit-user.component';
import { DialogsModule } from '../shared/dialogs.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    UserRoutingModule,
    MaterialCommonModule,
    DialogsModule
  ],
  declarations: [UserComponent, CreateUserComponent, ListUserComponent, EditUserComponent],
  providers: [
    RolesStorage,
    IconsService,
    UserService,
    ResponseServerService
    ]
})
export class UserModule { }
