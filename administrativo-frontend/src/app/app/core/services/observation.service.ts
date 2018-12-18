import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorage} from '../storage/token.storage';
import {Observation} from '../models/observation';

@Injectable()
export class ObservationService {

	serverName: string = '';
	pathController: string = '/api/process/execution/status/observation';
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

	save(observation: Observation): Observable<any> {
	    return this.http.post(`${this.fullPath}/create`, observation, this.httpOptions);
	  }

}
