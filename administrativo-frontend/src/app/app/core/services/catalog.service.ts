import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorage} from '../storage/token.storage';
import {CatalogValue} from '../models/catalog-value';

@Injectable()
export class CatalogService {

  serverName: string = '';
  pathController: string = '/api/catalog/catalogValue';
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

  save(catalogValue: CatalogValue): Observable<any> {
    return this.http.post(`${this.fullPath}/create`, catalogValue, this.httpOptions);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.fullPath}/delete?id=${id}`, this.httpOptions);
  }

  update(catalogValue: CatalogValue): Observable<any> {
    return this.http.put(`${this.fullPath}/update`, catalogValue, this.httpOptions);
  }

  /**
   * Find catalog value by keyword catalog.
   * @param catalogId keyword(Available values : TPP, STS)
   */
  findByCatalogId(catalogId: string): Observable<any> {
    const url = `${this.fullPath}/find?catalogKeyWord=${catalogId}`;
    return this.http.get(url, this.httpOptions);
  }

}