import { Injectable } from '@angular/core';


const TOKEN_KEY = 'AuthToken';
const ID_KEY = 'AuthID';

@Injectable()
export class TokenStorage {

  constructor() { }

  signOut() {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY,  token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveID(id: string) {
    window.sessionStorage.removeItem(ID_KEY);
    window.sessionStorage.setItem(ID_KEY,  id);
  }

  public getID(): string {
    return sessionStorage.getItem(ID_KEY);
  }

  public getStringAuth(): string{
    return "Bearer "+this.getToken();
  }
}