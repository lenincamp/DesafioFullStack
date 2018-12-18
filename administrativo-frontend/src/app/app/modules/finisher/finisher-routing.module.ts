import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FinisherComponent } from './pages/finisher/finisher.component';

const routes: Routes = [
	 {
	    path: 'finisher',
	    component: FinisherComponent
	}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FinisherRoutingModule { }
