import { Component, OnInit } from '@angular/core';
import { Messages } from '../../../../core/enum/messages.enum';
import { Roles } from '../../../../core/enum/roles.enum';
import { User } from '../../../../core/models/user';
import { UserService } from '../../../../core/services/user.service';
import { ResponseServerService } from '../../../../core/services/response-server.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'ot-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
	person:User;
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

	saveUser(){
		let user = Object.assign({},this.person);
		user.role = Roles[user.role];
		this.user_srv.save(user).subscribe(
	      data => {
	      	console.log(data);
	      	this.person = new User();
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

}
