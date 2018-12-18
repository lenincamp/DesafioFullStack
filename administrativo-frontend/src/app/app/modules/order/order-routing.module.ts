import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { MainMenuComponent } from './pages/main-menu/main-menu.component';

const routes: Routes = [
		{
		    path: 'order',
		    component: WelcomeComponent,
		    children: [
		    	
		    ]
		},
		{
			path: 'mainmenu', 
			component: MainMenuComponent
		}
	];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
