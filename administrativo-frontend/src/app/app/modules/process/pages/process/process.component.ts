import { Component, OnInit } from '@angular/core';
import { RolesStorage } from '../../../../core/storage/roles.storage';
import { IconsService } from '../../../../core/services/icons.service';

@Component({
  selector: 'ot-process',
  templateUrl: './process.component.html',
  styleUrls: ['./process.component.css']
})
export class ProcessComponent implements OnInit {
	moduleName:string = "PROCESS";
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
