import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class AuthenticationService {

  serverName: string = '';

  constructor(private http: HttpClient) {
  }

  attemptAuth(ussername: string, password: string): Observable<any> {
    const credentials = {usernameOrEmail: ussername, password: password};
    return this.http.post(this.serverName+'/api/auth/signin', credentials);
  }


}
