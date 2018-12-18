import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorage} from '../storage/token.storage';
import {Execution} from '../models/execution';

@Injectable()
export class ExecutionService {

  serverName: string = '';
  pathController: string = '/api/process/execution';
  fullPath: string = this.serverName + this.pathController;

  constructor(
    private http: HttpClient,
    private token: TokenStorage
  ) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.token.getStringAuth()
    })
  };

  save(execution: Execution): Observable<any> {
    let httpOptions = { headers: this.httpOptions.headers, observe: 'response' as 'response'};
    return this.http.post(`${this.fullPath}/create`, execution, httpOptions);
  }

  delete(id: number, processId: number): Observable<any> {
    return this.http.delete(`${this.fullPath}/delete?id=${id}&processId=${processId}`, this.httpOptions);
  }

  update(execution: Execution): Observable<any> {
    return this.http.put(`${this.fullPath}/update`, execution, this.httpOptions);
  }

  list_execution(processId, id, dateIni, dateFin): Observable<any> {
    const url = `${this.fullPath}/find_execution?processId=${processId}&id=${id}&dateIni=${dateIni}&dateFin=${dateFin}`;
    return this.http.get(url, this.httpOptions);
  }

}
