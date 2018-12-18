import { Injectable } from '@angular/core';
import * as _ from 'lodash';


const ROLES_KEY = 'Roles';

@Injectable()
export class RolesStorage {
  constructor() { }

  signOut() {
    window.sessionStorage.removeItem(ROLES_KEY);
    window.sessionStorage.clear();
  }

  public saveRolesMap(rolesMap: string) {
    window.sessionStorage.removeItem(ROLES_KEY);
    window.sessionStorage.setItem(ROLES_KEY,  JSON.stringify(rolesMap));
  }

  public getRolesMap(): JSON {
    return JSON.parse(sessionStorage.getItem(ROLES_KEY));
  }

  public getIdUser(): number {
    return (this.getRolesMap())[0].id;
  }

  public isProfile(profile): Boolean {
    return (this.getRolesMap())[0].name===profile;
  }

  public getActions(id): any {
    return JSON.parse(sessionStorage.getItem(ROLES_KEY))[0].modules.filter(obj=> {return obj.title==id;})[0].actions;
  }

  canCreate(moduleName){
    return this.doHaveAction(moduleName,"CAN CREATE");
  }

  canUpdate(moduleName){
    return this.doHaveAction(moduleName,"CAN UPDATE");
  }

  canRemove(moduleName){
    return this.doHaveAction(moduleName,"CAN REMOVE");
  }

  canRead(moduleName){
    return this.doHaveAction(moduleName,"CAN READ");
  }

  canOpine(moduleName){
    return this.doHaveAction(moduleName,"CAN GIVE A OPINION");
  }

  canExecute(moduleName){
    return this.doHaveAction(moduleName,"CAN EXECUTE");
  }
  

  doHaveAction(moduleName, action){
    /*
    let actions = this.getActions(moduleName);
    console.log("acciones", actions, "accion", action);
    console.log("comparacionx", _.filter(actions,act=>{console.log("compare into",act.description==action); return act.description==action;}));
    return _.filter(actions,act=>act.description==action).length>0? true:false;
    */
    let actions = this.getActions(moduleName);
    return _.find(actions, {'description':action})? true:false;
  }

  getTabs(moduleName){
    let navLinks:any= [];
    if(this.canCreate(moduleName))
    {
      navLinks.push({path:'./create', label:'CREATE'})
    }
    if(this.canRead(moduleName))
    {
      navLinks.push({path:'./list', label:'READ'})
    }
    return navLinks;
  }
}
