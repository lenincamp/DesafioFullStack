import { Component, OnInit } from '@angular/core';
import { RolesStorage } from '../storage/roles.storage';
import { Icons } from '../enum/icons.enum';
import {TokenStorage} from '../storage/token.storage';
import {Router} from '@angular/router';

@Component({
  selector: 'ot-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  modulos:any;
  
  constructor(private roles: RolesStorage, 
    private token: TokenStorage, 
    private router: Router) {
    this.modulos = roles.getRolesMap()[0].modules;
  }

  getIcon(id):string{
    return Icons[id];
  }

  ngOnInit() {
  }

  status: boolean = false;
  clickShowMenu(){
    this.status = !this.status;       
  }

  
  logout = () => {
    this.token.signOut();
    this.router.navigate(['/home']);
  }

}
