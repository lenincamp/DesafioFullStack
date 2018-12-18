import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenStorage } from '../storage/token.storage';

@Injectable()
export class ProcessService {

	serverName: string = '/api/process/';

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
		return this.http.post(this.serverName+'create', user, this.httpOptions);
	}

	delete(id:number): Observable<any> {
		return this.http.delete(this.serverName+'delete/'+id, this.httpOptions);
	}

	update(user:any): Observable<any> {
		return this.http.put(this.serverName+'update', user, this.httpOptions);
	}

	list(): Observable<any> {
		return this.http.get(this.serverName+'list_all', this.httpOptions);
	}
	
	findByUser(id:number): Observable<any> {
		return this.http.get(this.serverName+'find_by_user?userId='+id, this.httpOptions);
	}

	findByProcessId(id:number): Observable<any> {
		return this.http.get(this.serverName+'find_by_processId?processId='+id, this.httpOptions);
	}
}
