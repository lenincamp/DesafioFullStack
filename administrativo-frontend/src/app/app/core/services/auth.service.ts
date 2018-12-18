import {Injectable} from '@angular/core';
import {TokenStorage} from '../../core/storage/token.storage';

@Injectable()
export class AuthService {
  constructor(private token: TokenStorage) {}
  public isAuthenticated(): boolean {
    const token = this.token.getToken();
    return token != null && token.length > 0;
  }
}
