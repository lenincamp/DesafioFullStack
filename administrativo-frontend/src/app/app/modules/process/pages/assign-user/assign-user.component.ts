import { Component, OnInit, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Messages } from '../../../../core/enum/messages.enum';
import { User } from '../../../../core/models/user';
import { Status } from '../../../../core/models/status';
import { Process } from '../../../../core/models/process';
import { Execution } from '../../../../core/models/execution';
import { Roles } from '../../../../core/enum/roles.enum';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { StatusService } from '../../../../core/services/status.service';
import { ExecutionService } from '../../../../core/services/execution.service';
import { UserService } from '../../../../core/services/user.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

declare var jquery:any;
declare var $ :any;
import * as _ from 'lodash';

@Component({
  selector: 'assign-user',
  templateUrl: './assign-user.component.html',
  styleUrls: ['./assign-user.component.css']
})
export class AssignUserComponent implements OnInit {
  @Input() status:Status;
	@Input() processToAssign:Process;
	listUser:User[];
  listUserSelected:User[]=[];
  execution:Execution;
  
  constructor(
  	public roles_stg: RolesStorage,
		public snackBar: MatSnackBar,
		public execution_srv: ExecutionService,
		public user_srv: UserService,
		public response_svr: ResponseServerService,
    public status_srv: StatusService
		) {
  			this.loadUser();
        this.execution = new Execution();
        this.execution.createAt = new Date();
		}

  ngOnInit() {
    this.processToAssign = new Process();    
  }

  loadUser(){
    if(!this.roles_stg.isProfile(Roles.ROLE_USER_FINAL))
    	this.user_srv.list('3').subscribe(
        data => {
        	this.listUser = data.dataCol;
        },
        err => {
        	let message = this.response_svr.getErrorMessage(err);
        	this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
  	      duration: 6000,
  	    });
        }
      );
  }

  removeUser(user){
    _.remove(this.listUserSelected, user);
  }

  addUser(user){
    if(!_.find(this.listUserSelected,user))
      this.listUserSelected.push(user);
  }

  saveExecution(){
    this.execution.processId = +this.processToAssign.id;
    this.execution_srv.save(this.execution).subscribe(
      data => {
        let status = new Status();
        status.executionId = +_.last(_.split(data.headers.get('location'),'/'));
        status.users = this.listUserSelected;
        this.saveAssing(status);
      },
      err => {
        let message = this.response_svr.getErrorMessage(err);
        this.snackBar.open(message!=""?message:Messages.ERROR, Messages.EMPTY, {
        duration: 6000,
        verticalPosition:'top'
      });
      }
    );
  }

  saveAssing(status:Status){
    this.status_srv.save(status).subscribe(
      data => {
        //this.reset();
        $('#assignModal').modal('hide');
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

}
