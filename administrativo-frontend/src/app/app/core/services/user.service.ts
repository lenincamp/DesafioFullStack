import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenStorage } from '../storage/token.storage';
import { User } from '../models/user';

@Injectable()
export class UserService {

	serverName: string = '/api/user/';

	constructor(
		private http: HttpClient,
		private token: TokenStorage
		) {
	}

	httpOptions = {
	  headers: new HttpHeaders({
	    'Content-Type':  'application/json',
	    'Authorization': this.token.getStringAuth()
	  })
	};

	save(user:any): Observable<any> {
		return this.http.post('/api/auth/signup', user, this.httpOptions);
	}

	delete(id:number): Observable<any> {
		return this.http.delete(this.serverName+'delete?id='+id, this.httpOptions);
	}

	update(user:User, rolId:number): Observable<any> {
		console.log("usuario", user);
		console.log("rolId", rolId);
		return this.http.put(this.serverName+`update/${user.id}/${rolId}`, user, this.httpOptions);
	}

	list(role?:string): Observable<any> {
		console.log("header",this.httpOptions);
		return this.http.get(this.serverName+'find?enabled=true'+(role?`&roleId=${role}`:""), this.httpOptions);
	}

	findById(id:number): Observable<any> {
		return this.http.get(this.serverName+'find_by_id/'+id, this.httpOptions);
	}
}
