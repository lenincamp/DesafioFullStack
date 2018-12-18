import { Injectable } from '@angular/core';
import { Icons } from '../enum/icons.enum';

@Injectable()
export class IconsService {

	constructor() { }

	getIcon(id){
		return Icons[id];
	}

}
