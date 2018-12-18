import { Component, OnInit, Input } from '@angular/core';
import { Messages } from '../../../../core/enum/messages.enum';
import { Roles } from '../../../../core/enum/roles.enum';
import { User } from '../../../../core/models/user';
import { UserService } from '../../../../core/services/user.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

declare var jquery:any;
declare var $ :any;

@Component({
  selector: 'edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
	@Input() person:User;
	roles = Roles;
	constructor(
		private user_srv: UserService,
		public snackBar: MatSnackBar,
		public response_svr: ResponseServerService
		) {
		this.person = new User();
	}
	ngOnInit() {
	}

  	getRoles(){
		return Object.keys(this.roles);
	}


	updateUser(){
		let user = Object.assign({},this.person);
		user.role = Roles[user.role];
		this.user_srv.update(user,this.getRoleID(user.role)).subscribe(
	      data => {
	      	$('#editmodal').modal('hide');
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

	resetForm(){
		this.person = new User();
	}

	getRoleID(rol){
		let id = 0;
		switch (rol) {
			case Roles.ROLE_ADMIN:
				id = 1;
				break;
			case Roles.ROLE_USER:
				id = 2;
				break;
			case Roles.ROLE_USER_FINAL:
				id = 3;
				break;
		}
		return id;
	}

}
