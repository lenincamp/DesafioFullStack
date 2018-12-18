import { Component, OnInit, ViewChild} from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Messages } from '../../../../core/enum/messages.enum';
import { Process } from '../../../../core/models/process';
import { Observation } from '../../../../core/models/observation';
import { Roles } from '../../../../core/enum/roles.enum';
import { Status } from '../../../../core/models/status';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { TokenStorage } from '../../../../core/storage/token.storage';
import { IconsService } from '../../../../core/services/icons.service';
import { ProcessService } from '../../../../core/services/process.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

declare var jquery:any;
declare var $ :any;
import * as _ from 'lodash';

@Component({
  selector: 'ot-list-process',
  templateUrl: './list-process.component.html',
  styleUrls: ['./list-process.component.css']
})
export class ListProcessComponent implements OnInit {
  displayedColumns = ['id','name','type','action'];
  dataSource: MatTableDataSource<Process>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

	moduleName:string = "PROCESS";
	currentProcess:Process;
	currentIdDelete="";
	currentIdEdit="";

	
	constructor(
		public roles_stg: RolesStorage,
		public token_stg: TokenStorage,
		public snackBar: MatSnackBar,
		public icon_svr: IconsService,
		public process_srv: ProcessService,
		public response_svr: ResponseServerService
		) {

		// Assign the data to the data source for the table to render
		//this.dataSource = new MatTableDataSource(users);
		this.getAllProcess();
		let self =this;
	    $(document).on('hide.bs.modal','#editmodal', function () {
	        self.getAllProcess();
	    });
	}

	ngOnInit() {
	}

	

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  openModalDelete(id){
  	this.currentIdDelete=id;
  	$('#deletemodal').modal('show');
  }

  openModalEdit(id){
  	this.currentIdEdit=id;
  	$('#editmodal').modal('show');
  }

  openModalAssign(process){
  	this.currentProcess = process;
  	$('#assignModal').modal('show');
  }

  canAddObsv=true;
  othersObsrv:Observation[]=[];
  openModalConclusion(process){
  	
  	if(this.roles_stg.isProfile(Roles.ROLE_USER)){
  		this.process_srv.findByProcessId(+process.id).subscribe(
	      data => {
	      	let processComplete = data.dataCol[0];
		    this.currentProcess = processComplete;
		  	this.othersObsrv = processComplete.executions[0].statuses[0].observations?processComplete.executions[0].statuses[0].observations:[];
		  	this.canAddObsv = false;
	  		$('#observationModal').modal('show');
	      },
	      err => {
	      	let message = this.response_svr.getErrorMessage(err);
	      	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
		      duration: 6000,
		    });
	      }
	    );
  	}
  	else if(this.roles_stg.isProfile(Roles.ROLE_USER_FINAL)){
  		this.currentProcess = process;
	  	this.othersObsrv = process.executions[0].statuses[0].observations?process.executions[0].statuses[0].observations:[];
	  	console.log(process.executions[0].statuses[0].observations,{userId:+this.token_stg.getID()});
	  	console.log(_.find(process.executions[0].statuses[0].observations,{userId:+this.token_stg.getID()}));
	  	this.canAddObsv = _.find(process.executions[0].statuses[0].observations,{userId:+this.token_stg.getID()})?false:true;
	  	$('#observationModal').modal('show');
  	}
  }

  deleteProcess(id){
  	this.deleteBDDProcess(id);
  }

  /*DATA BASE ACTIONS*/
	getAllProcess(){
		if(this.roles_stg.isProfile(Roles.ROLE_USER)){
			this.process_srv.list().subscribe(
		      data => {
		      	this.dataSource = new MatTableDataSource(data.dataCol);
		      	this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
		      },
		      err => {
		      	let message = this.response_svr.getErrorMessage(err);
		      	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
			      duration: 6000,
			    });
		      }
		    );
	    }
	  	else if(this.roles_stg.isProfile(Roles.ROLE_USER_FINAL)){
	  		this.process_srv.findByUser(+this.token_stg.getID()).subscribe(
		      data => {
		      	this.dataSource = new MatTableDataSource(data.dataCol);
		      	this.dataSource.paginator = this.paginator;
				this.dataSource.sort = this.sort;
		      },
		      err => {
		      	let message = this.response_svr.getErrorMessage(err);
		      	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
			      duration: 6000,
			    });
		      }
		    );
	  	}
	}

	deleteBDDProcess(id){
  		this.process_srv.delete(id).subscribe(
	      data => {
		    $('#prc_'+id).remove();
	  		$('#deletemodal').modal('hide');
		  	this.snackBar.open(Messages.SUCCESS_DELETE, Messages.EMPTY, {
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
}