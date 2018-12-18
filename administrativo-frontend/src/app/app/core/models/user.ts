import {Role} from './role';

export class User {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: string;
  userName: string;
  fullName: string;
  id: number;
  roles: Role[];

  getRoles(){
  	let rolesList="";
    let separador="";
    this.roles.forEach(rol=>{
      rolesList+=separador+rol.name;
      separador=", ";
    });
    return rolesList;
  }
}
