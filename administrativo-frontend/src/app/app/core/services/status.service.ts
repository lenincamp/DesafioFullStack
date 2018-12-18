import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorage} from '../storage/token.storage';
import {Status} from '../models/status';

@Injectable()
export class StatusService {

	serverName: string = '';
	pathController: string = '/api/process/execution/status';
	fullPath: string = this.serverName + this.pathController;

	constructor(
		private http: HttpClient,
	    private token: TokenStorage
    ) { }


	  httpOptions = {
	    headers: new HttpHeaders({
	      'Content-Type': 'application/json',
	      'Authorization': this.token.getStringAuth()
	    })
	  };

	save(status: Status): Observable<any> {
	    let httpOptions = { headers: this.httpOptions.headers, observe: 'response' as 'response'};
	    return this.http.post(`${this.fullPath}/create`, status, httpOptions);
	  }

}
