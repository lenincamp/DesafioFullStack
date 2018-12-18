import { Component, OnInit, Input } from '@angular/core';
import { Process } from '../../../../core/models/process';
import { ProcessService } from '../../../../core/services/process.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { Messages } from '../../../../core/enum/messages.enum';
import { MatSnackBar } from '@angular/material';
import { CatalogValue } from '../../../../core/models/catalog-value';

@Component({
  selector: 'edit-process',
  templateUrl: './edit-process.component.html',
  styleUrls: ['./edit-process.component.css']
})
export class EditProcessComponent implements OnInit {

	@Input() idProcess:number;
	process:Process;
	tipos:CatalogValue[];
	constructor(
		public snackBar: MatSnackBar,
		public process_srv: ProcessService,
		public response_svr: ResponseServerService
		) { 
		this.process = new Process();
	}

	ngOnInit() {
	}

	
  updateProcess(){
		this.process_srv.update(this.process).subscribe(
	      data => {
	      	console.log(data);
	      	this.reset();
	      	this.snackBar.open(Messages.SUCCESS_SAVE, Messages.EMPTY, {
		      duration: 4000,
		    });
	      },
	      err => {
	      	let message = this.response_svr.getErrorMessage(err);
	      	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
		      duration: 6000,
		    });
	      }
	    );
	}

	reset(){
		this.process = new Process();
	}
}
