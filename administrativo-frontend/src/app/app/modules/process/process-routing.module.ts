import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProcessComponent } from './pages/process/process.component';
import { CreateProcessComponent } from './pages/create-process/create-process.component';
import { ListProcessComponent } from './pages/list-process/list-process.component';

const routes: Routes = [
	{ 
      path: '',
      redirectTo: 'process',
      pathMatch: 'full'
    },
	{
	    path: 'process',
	    component: ProcessComponent,
	    children: [
		    {
		    	path: 'create',
	    		component: CreateProcessComponent,
		    },
		    {
		    	path: 'list',
	    		component: ListProcessComponent,
		    }
	    ]
	}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProcessRoutingModule { }
