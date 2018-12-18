import { Component, OnInit } from '@angular/core';
import { Messages } from '../../../../core/enum/messages.enum';
import { Process } from '../../../../core/models/process';
import { CatalogValue } from '../../../../core/models/catalog-value';
import { CatalogService } from '../../../../core/services/catalog.service';
import { ProcessService } from '../../../../core/services/process.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'ot-create-process',
  templateUrl: './create-process.component.html',
  styleUrls: ['./create-process.component.css']
})
export class CreateProcessComponent implements OnInit {

	process:Process;
	tipos:CatalogValue[];
	constructor(
		private process_srv: ProcessService,
		public snackBar: MatSnackBar,
		public response_svr: ResponseServerService,
		public catalog_svr: CatalogService
		) {
		this.process = new Process();
		this.loadCatalog();
	}
	
	ngOnInit() {
	}

	saveProcess(){
		this.process_srv.save(this.process).subscribe(
	      
	      (resp: Response) => {
	      	console.log("location",resp.headers);
	      	this.reset();
	      	this.snackBar.open(Messages.SUCCESS_SAVE, Messages.EMPTY, {
		      duration: 4000
		    });
	      },
	      err => {
	      	let message = this.response_svr.getErrorMessage(err);
	      	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
		      duration: 6000
		    });
	      }
	    );
	}

	loadCatalog(){
		this.catalog_svr.findByCatalogId("TPP").subscribe(
	      data => {
	      	this.tipos=data.dataCol;
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
