import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateUserComponent } from './pages/create-user/create-user.component';
import { UserComponent } from './pages/user/user.component';
import { ListUserComponent } from './pages/list-user/list-user.component';


const routes: Routes = [
        { 
          path: '',
          redirectTo: 'users/create',
          pathMatch: 'full'
        },
        {
            path: 'users',
            component: UserComponent,
            children: [
                {
                    path: 'create',
                    component: CreateUserComponent,
                },
                {
                    path: 'list',
                    component: ListUserComponent,
                }
            ]
        }
    ];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
