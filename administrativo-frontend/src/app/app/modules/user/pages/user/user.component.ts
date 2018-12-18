import { Component, OnInit } from '@angular/core';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { IconsService } from '../../../../core/services/icons.service';

@Component({
  selector: 'ot-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

	moduleName:string = "USERS";
	navLinks:any= [];
  constructor(
  	public roles: RolesStorage,
  	public icon_svr: IconsService
  	) { 
  	this.navLinks = this.roles.getTabs(this.moduleName);
  }

  ngOnInit() {
  }

}
