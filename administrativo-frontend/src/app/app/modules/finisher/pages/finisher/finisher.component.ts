import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Messages } from '../../../../core/enum/messages.enum';
import { Process } from '../../../../core/models/process';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { IconsService } from '../../../../core/services/icons.service';
import { ProcessService } from '../../../../core/services/process.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

declare var jquery:any;
declare var $ :any;

@Component({
  selector: 'ot-finisher',
  templateUrl: './finisher.component.html',
  styleUrls: ['./finisher.component.css']
})
export class FinisherComponent implements OnInit {
  displayedColumns = ['id','name','action'];
  dataSource: MatTableDataSource<Process>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

	moduleName:string = "PROCESS";
	currentIdDelete="";
	currentIdEdit="";
	
	constructor(
		public roles: RolesStorage,
		public snackBar: MatSnackBar,
		public icon_svr: IconsService,
		public process_srv: ProcessService,
		public response_svr: ResponseServerService
		) {

	// Assign the data to the data source for the table to render
	//this.dataSource = new MatTableDataSource(users);
	this.getAllProcess();
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

  deleteProcess(id){
  	this.deleteBDDProcess(id);
  }

  /*DATA BASE ACTIONS*/
	getAllProcess(){
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