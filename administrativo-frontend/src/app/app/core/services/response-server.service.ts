import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable()
export class ResponseServerService {

  constructor() { }

  getErrorMessage(response:JSON){
  	let mensaje = "";
  	let errorList = response["error"];
    console.log("response",response);
  	console.log("errorList",errorList.errors);
  	if(errorList.errors){
  		errorList.errors.forEach(err=>mensaje+=`${err.field}: ${err.defaultMessage}. `);
  	}else{
  		mensaje+=errorList.message;
  	}
  	return mensaje;
  }

}
